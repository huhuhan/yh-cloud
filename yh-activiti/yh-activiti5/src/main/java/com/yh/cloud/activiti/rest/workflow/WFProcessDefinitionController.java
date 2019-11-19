package com.yh.cloud.activiti.rest.workflow;

import com.yh.cloud.activiti.service.workflow.WFProcessDefinitionService;
import com.yh.cloud.web.wrapper.ReturnWrapMapper;
import com.yh.cloud.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程定义管理
 *
 * @author yanghan
 * @date 2019/10/29
 */
@Api(tags = "流程定义管理")
@RestController
@RequestMapping(value = "/workflow/process-definition")
@Slf4j
public class WFProcessDefinitionController {
    @Autowired
    private WFProcessDefinitionService wfProcessDefinitionService;

    @ApiOperation("流程定义|流程部署 列表")
    @GetMapping(value = "/list")
    public ReturnWrapper getList(@RequestParam(value = "pageSize", defaultValue = "1000", required = false) Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum) {
        return ReturnWrapMapper.ok(wfProcessDefinitionService.getList(pageSize, pageNum));
    }


    @ApiOperation("挂起、激活流程")
    @PostMapping(value = "/update/state")
    public ReturnWrapper updateState(@RequestParam String state,
                                     @RequestParam String processDefinitionId) {
        try {
            return ReturnWrapMapper.ok(wfProcessDefinitionService.updateState(state, processDefinitionId));
        } catch (Exception e) {
            return ReturnWrapMapper.errorWithException(new Exception("state: active|suspend"));
        }
    }

    @ApiOperation("删除流程定义，级联删除流程实例")
    @PostMapping(value = "/delete")
    public ReturnWrapper delete(@RequestParam("deploymentId") String deploymentId) {
        wfProcessDefinitionService.delete(deploymentId);
        return ReturnWrapMapper.ok();
    }

    @ApiOperation("上传文件部署流程")
    @PostMapping(value = "/deploy")
    public ReturnWrapper deployByUpload(@RequestParam(value = "file") MultipartFile file) {
        try {
            wfProcessDefinitionService.deployByUpload(file);
            return ReturnWrapMapper.ok();
        } catch (Exception e) {
            log.error("error on deploy process, because of file input stream", e);
            return ReturnWrapMapper.errorWithException(e);
        }
    }

    @ApiOperation("流程转模型")
    @PostMapping(value = "/convert")
    public ReturnWrapper convertToModel(@RequestParam("processDefinitionId") String processDefinitionId) {
        try {
            wfProcessDefinitionService.convertToModel(processDefinitionId);
            return ReturnWrapMapper.ok();
        } catch (Exception e) {
            log.error("error convert to model", e);
            return ReturnWrapMapper.errorWithException(e);
        }
    }
}
