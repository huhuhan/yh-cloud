package com.yh.cloud.activiti.rest.act;

import com.google.common.collect.Maps;
import com.yh.cloud.activiti.fegin.ActProcessInstanceFegin;
import com.yh.cloud.activiti.model.vo.CompleteTaskVo;
import com.yh.cloud.activiti.model.vo.StartProcessInstanceVo;
import com.yh.cloud.activiti.model.vo.TaskResultVo;
import com.yh.cloud.activiti.service.WorkFlowService;
import com.yh.cloud.activiti.util.WorkflowUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 工作流交互接口
 *
 * @author yanghan
 * @date 2019/11/6
 */
@Api(tags = "工作流API，建议后端Feign调用")
@RestController
@RequestMapping("/act/oa")
//@ApiIgnore
public class ActProcessInstanceController implements ActProcessInstanceFegin {

    @Autowired
    private WorkFlowService workFlowService;

    @ApiOperation("启动流程实例")
    @PostMapping("start")
    @Override
    public String startWorkFlow(StartProcessInstanceVo startProcessInstanceVo) {
        ProcessInstance processInstance = workFlowService.start(startProcessInstanceVo);
        return processInstance.getId();
    }

    @ApiOperation("签收任务")
    @PostMapping("task/claim")
    @Override
    public void taskClaim(@RequestParam String userId, @RequestParam String taskId) {
        workFlowService.taskClaim(userId, taskId);
    }

    @ApiOperation("完成任务")
    @PostMapping("task/complete")
    @Override
    public void taskComplete(CompleteTaskVo completeTaskVo) {
        workFlowService.taskComplete(completeTaskVo);
    }

    @ApiOperation("查询代办任务")
    @GetMapping("task")
    @Override
    public List<TaskResultVo> taskList(String userId) {
        return workFlowService.taskList(userId);
    }

    @ApiOperation("根据实例ID获取跟踪节点图片")
    @GetMapping("trace-img")
    public Map<String, Object> getTraceNodeImgById(String processInstanceId) {
        Map<String, Object> shineProImages = Maps.newHashMap();
        InputStream imageStream = workFlowService.generateStream(processInstanceId, true);
        shineProImages.put("imageCurrentNode", WorkflowUtils.encodeToString(imageStream));
        InputStream imageStream2 = workFlowService.generateStream(processInstanceId, false);
        shineProImages.put("imageNoCurrentNode", WorkflowUtils.encodeToString(imageStream2));
        return shineProImages;
    }


    @ApiOperation("获取任务附带信息")
    @GetMapping("task-variables")
    @Override
    public Map<String, Object> getTaskVariables(String taskId) {
        return workFlowService.getTaskVariables(taskId);
    }
}
