package com.yh.cloud.activiti.leave.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;
import com.yh.cloud.activiti.fegin.ActProcessInstanceFegin;
import com.yh.cloud.activiti.leave.mapper.UserLeaveMapper;
import com.yh.cloud.activiti.leave.model.domain.UserLeave;
import com.yh.cloud.activiti.leave.model.dto.CheckMsgDto;
import com.yh.cloud.activiti.leave.service.IUserLeaveService;
import com.yh.cloud.activiti.model.vo.CompleteTaskVo;
import com.yh.cloud.activiti.model.vo.StartProcessInstanceVo;
import com.yh.cloud.activiti.model.vo.TaskResultVo;
import com.yh.cloud.base.util.BeanMapUtil;
import com.yh.cloud.web.model.entity.CurrentUser;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 请假表 服务实现类
 * </p>
 *
 * @author yanghan
 * @since 2019-10-25
 */
@Service
public class UserLeaveServiceImpl extends ServiceImpl<UserLeaveMapper, UserLeave> implements IUserLeaveService {

    @Autowired
    private UserLeaveMapper userLeaveMapper;
    @Autowired
    private ActProcessInstanceFegin actProcessInstanceFegin;
    /** 审核信息列表 */
    private final String CHECK_MSG_LIST = "CHECK_MSG_LIST";
    /** 实例信息 */
    private final String BASE_TASK_INFO = "baseTask";
    /** 重新申请次数限制 */
    private final int RESTART_COUNT_LIMIT = 2;

    @Override
    public void addUserLeave(CurrentUser currentUser, UserLeave userLeave) {
        userLeave.setUserId(Long.valueOf(currentUser.getUserId()));

//        Map<String, Object> map = new HashMap<>();
//        map.put("baseTask", userLeave);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_leave", map);
//        userLeave.setProcessInstanceId(processInstance.getId());
        userLeaveMapper.insert(userLeave);

        StartProcessInstanceVo startProcessInstanceVo = new StartProcessInstanceVo();
        startProcessInstanceVo.setProcessDefinitionKey("leave");
        startProcessInstanceVo.setBusinessKey(userLeave.getId().toString());
        startProcessInstanceVo.setUserId(userLeave.getUserId().toString());
        //todo: 先转化为Map，id要String类型，否则太长前端会流失字节
        Map<String, Object> map = BeanMapUtil.beanToMap2(userLeave);
        map.put("id", userLeave.getId().toString());
        startProcessInstanceVo.getVariables().put(BASE_TASK_INFO, map);
        String processInstanceId = actProcessInstanceFegin.startWorkFlow(startProcessInstanceVo);
        userLeave.setProcessInstanceId(processInstanceId);
        userLeaveMapper.updateById(userLeave);
    }

    @Override
    public List<Map<String, Object>> userLeaveList(CurrentUser currentUser) {
        List<Map<String, Object>> result = Lists.newArrayList();
        List<TaskResultVo> taskResultVos = actProcessInstanceFegin.taskList(currentUser.getUserId().toString());
        Map<Long, TaskResultVo> taskMap = taskResultVos.stream().collect(Collectors.toMap(obj -> new Long(obj.getBusinessKey()), obj -> obj));

        List<Long> businessKeyIds = taskResultVos.stream().map(task -> new Long(task.getBusinessKey())).collect(Collectors.toList());
        if (businessKeyIds.isEmpty()) {
            return result;
        }
        List<UserLeave> userLeaves = userLeaveMapper.selectBatchIds(businessKeyIds);
        for (UserLeave userLeave : userLeaves) {
            //todo：demo版，就不写vo对象了
            Map<String, Object> map = BeanMapUtil.beanToMap2(userLeave);
            TaskResultVo taskResultVo = taskMap.get(userLeave.getId());
            //发起人等于当前班里人，显示编辑按钮
            String leaveUserId = MapUtils.getString(taskResultVo.getVariables(), "applyUserId");
            if (currentUser.getUserId().toString().equals(leaveUserId)) {
                map.put("showEditBtn", true);
            } else {
                map.put("showEditBtn", false);
            }
            if (null != taskResultVo) {
                map.put("actTask", taskResultVo);
            }
            map.put("id", userLeave.getId().toString());
            result.add(map);
        }
        return result;
    }

    /**
     * 完成任务
     * 1、进入下个流程
     * 2、返回上个流程
     * 3、结束流程
     * @param currentUser
     * @param param 必须参数【taskId、passFlag、checkMsg】
     */
    @Override
    public void completeLeaveTask(CurrentUser currentUser, Map<String, Object> param) {
        String taskId = MapUtils.getString(param, "taskId");
        boolean passFlag = MapUtils.getBooleanValue(param, "passFlag");
        String checkMsg = MapUtils.getString(param, "checkMsg");
        boolean restart = MapUtils.getBooleanValue(param, "restart");
        Map<String, Object> variables = actProcessInstanceFegin.getTaskVariables(taskId);
        Map<String, Object> baseTask = (Map<String, Object>) variables.get(BASE_TASK_INFO);
        //调整申请
        Integer restartCount = MapUtils.getInteger(variables, "restartCount", 0);
        if (restart) {
            if (passFlag) {
                if (restartCount > RESTART_COUNT_LIMIT) {
                    passFlag = false;
                    checkMsg = "已经超过调整申请次数，直接结束";
                } else {
                    restartCount++;
                    //更新申请内容
                    Map<String, Object> form = (Map<String, Object>) param.get("form");
                    baseTask = this.editUserLeave(form);
                }
            } else {
                //直接取消申请
            }
        }

        //审核信息集合，添加当前任务的审核信息
        List<CheckMsgDto> checkMsgList = (List<CheckMsgDto>) variables.get(CHECK_MSG_LIST);
        if (null == checkMsgList) {
            checkMsgList = Lists.newArrayList();
        }
        CheckMsgDto checkMsgDto = new CheckMsgDto();
        checkMsgDto.setUserId(currentUser.getUserId());
        checkMsgDto.setUserName(currentUser.getUsername());
        checkMsgDto.setCheckMsg(checkMsg);
        checkMsgDto.setCheckTime(LocalDateTime.now());
        checkMsgList.add(checkMsgDto);


        //办理任务
        CompleteTaskVo completeTaskVo = new CompleteTaskVo();
        completeTaskVo.setTaskId(taskId);
        Map<String, Object> map = Maps.newHashMap();
        map.put("passFlag", passFlag);
        map.put("restartCount", restartCount);
        map.put(CHECK_MSG_LIST, checkMsgList);
        map.put(BASE_TASK_INFO, baseTask);
        completeTaskVo.setVariables(map);
        actProcessInstanceFegin.taskComplete(completeTaskVo);
    }

    private Map<String, Object> editUserLeave(Map<String, Object> form) {
        if(null != form) {
            Long id = MapUtils.getLong(form, "id");
            UserLeave userLeave = userLeaveMapper.selectById(id);
            if(null != userLeave){
                userLeave.setBeginTime(LocalDateTime.parse(MapUtils.getString(form, "beginTime")));
                userLeave.setEndTime(LocalDateTime.parse(MapUtils.getString(form, "endTime")));
                userLeave.setReason(MapUtils.getString(form, "reason"));
                userLeaveMapper.updateById(userLeave);
                //todo: 先转化为Map，id要String类型，否则太长前端会流失字节
                Map<String, Object> map = BeanMapUtil.beanToMap2(userLeave);
                map.put("id", userLeave.getId().toString());
                return map;
            }
        }
        return null;

    }
}
