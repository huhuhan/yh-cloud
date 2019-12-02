package com.yh.cloud.activiti.fegin;

import com.yh.cloud.activiti.model.vo.CompleteTaskVo;
import com.yh.cloud.activiti.model.vo.StartProcessInstanceVo;
import com.yh.cloud.activiti.model.vo.TaskResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 业务交互接口
 *
 * @author yanghan
 * @date 2019/11/31
 */
@FeignClient(name = "YH-ACTIVITI5", contextId = "act")
public interface ActProcessInstanceFegin {

    /**
     * 开启实例
     *
     * @param startProcessInstanceVo
     * @return
     */
    @PostMapping("/act/oa/start")
    String startWorkFlow(@RequestBody StartProcessInstanceVo startProcessInstanceVo);


    /**
     * 签收任务
     *
     * @param userId
     * @param taskId
     */
    @PostMapping("/act/oa/task/claim")
    void taskClaim(@RequestParam("userId") String userId,
                   @RequestParam("taskId") String taskId);

    /**
     * 完成任务
     *
     * @param completeTaskVo
     */
    @PostMapping("/act/oa/task/complete")
    void taskComplete(@RequestBody CompleteTaskVo completeTaskVo);

    /**
     * 查询待办任务
     *
     * @param userId
     * @return
     */
    @GetMapping("/act/oa/task")
    List<TaskResultVo> taskList(@RequestParam("userId") String userId);

    /**
     * 查询运行中实例
     *
     * @param processDefinitionKey
     * @param pageNum
     * @param pageSize
     * @return
     */
//    @GetMapping("/act/oa/running")
//    List<RunningResultVo> runningList(@RequestParam("processDefinitionKey") String processDefinitionKey,
//                                      @RequestParam("pageNum") Integer pageNum,
//                                      @RequestParam("pageSize") Integer pageSize);

    /**
     * 查询已结束实例
     *
     * @param processDefinitionKey
     * @param pageNum
     * @param pageSize
     * @return
     */
//    @GetMapping("/act/oa/finished")
//    List<FinishedResultVo> finishedList(@RequestParam("processDefinitionKey") String processDefinitionKey,
//                                        @RequestParam("pageNum") Integer pageNum,
//                                        @RequestParam("pageSize") Integer pageSize);


    /**
     * 获取任务附带信息
     *
     * @param taskId
     * @return
     */
    @GetMapping("/act/oa/task-variables")
    Map<String, Object> getTaskVariables(@RequestParam("taskId") String taskId);

}
