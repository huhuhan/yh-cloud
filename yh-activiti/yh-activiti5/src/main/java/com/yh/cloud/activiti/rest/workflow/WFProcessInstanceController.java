package com.yh.cloud.activiti.rest.workflow;

import com.github.pagehelper.PageInfo;
import com.yh.cloud.activiti.model.vo.ProcessQueryVo;
import com.yh.cloud.activiti.service.workflow.WFProcessInstanceService;
import com.yh.cloud.web.wrapper.ReturnWrapMapper;
import com.yh.cloud.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 流程实例管理
 *
 * @author yanghan
 * @date 2019/10/29
 */
@Api(tags = "流程实例管理")
@RestController
@RequestMapping(value = "/workflow/process-instance")
public class WFProcessInstanceController {
    @Autowired
    private WFProcessInstanceService wfProcessInstanceService;

    @ApiOperation("运行实例列表")
    @GetMapping(value = "/running-list")
    public ReturnWrapper<PageInfo> getRunningList(ProcessQueryVo processQueryVo) {
        return ReturnWrapMapper.ok(wfProcessInstanceService.runningPSList(processQueryVo));
    }

    @ApiOperation("历史实例列表")
    @GetMapping(value = "/history-list")
    public ReturnWrapper<PageInfo> getHistoryList(ProcessQueryVo processQueryVo) {
        return ReturnWrapMapper.ok(wfProcessInstanceService.historyPSList(processQueryVo));
    }

    @ApiOperation("挂起、激活流程")
    @PostMapping(value = "/update/state")
    public ReturnWrapper updateState(@RequestParam String state,
                                     @RequestParam String processInstanceId) {
        try {
            return ReturnWrapMapper.ok(wfProcessInstanceService.updateState(state, processInstanceId));
        } catch (Exception e) {
            return ReturnWrapMapper.errorWithException(new Exception("state: active|suspend"));
        }
    }

    @ApiOperation("删除流程实例")
    @PostMapping(value = "/delete")
    public ReturnWrapper delete(@RequestParam("processInstanceId") String processInstanceId) {
        wfProcessInstanceService.delete(processInstanceId);
        return ReturnWrapMapper.ok();
    }
}
