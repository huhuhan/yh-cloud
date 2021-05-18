package com.yh.cloud.activiti.rest.workflow;

import com.yh.cloud.activiti.service.workflow.WFModelService;
import com.yh.common.web.wrapper.ReturnMsg;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 模型管理
 *
 * @author yanghan
 * @date 2019/10/29
 */
@Api(tags = "模型管理")
@RestController
@RequestMapping(value = "/workflow/model")
@Slf4j
public class WFModelController {

    @Autowired
    private WFModelService wfModelService;


    @ApiOperation("模型列表")
    @GetMapping("list")
    public ReturnWrapper getList(@RequestParam(value = "pageSize", defaultValue = "1000", required = false) Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum) {
        return ReturnWrapMapper.ok(wfModelService.getList(pageSize, pageNum));
    }

    @ApiOperation("创建模型")
    @PostMapping("create")
    public ReturnWrapper create(@RequestParam("name") String name,
                                @RequestParam("key") String key,
                                @RequestParam("description") String description,
                                HttpServletRequest request) {
        try {
            Model modelData = wfModelService.create(name, key, description);
            String redirectUrl = request.getContextPath() + "/modeler.html?modelId=" + modelData.getId();
            log.info("访问地址编辑新模型：" + redirectUrl);
            return ReturnWrapMapper.ok(new ReturnMsg().keyValue("redirectUrl", redirectUrl));
        } catch (Exception e) {
            log.error("创建模型失败：", e);
            return ReturnWrapMapper.errorWithException(e);
        }
    }

    @ApiOperation("部署流程（BPMN转为流程定义）")
    @GetMapping("deploy/{modelId}")
    public ReturnWrapper deploy(@PathVariable("modelId") String modelId) {
        try {
            Deployment deployment = wfModelService.deploy(modelId);
            log.info("根据模型部署流程失败：modelId={}", deployment.getId());
            // todo
            return ReturnWrapMapper.ok(null == deployment ? null : null);
        } catch (Exception e) {
            log.error("根据模型部署流程失败：modelId={}", modelId, e);
            return ReturnWrapMapper.errorWithException(e);
        }
    }

    @ApiOperation("部署流程写死demo")
    @GetMapping("deploy/demo")
    public ReturnWrapper deployDemo() {
        return ReturnWrapMapper.ok(wfModelService.deployDemo());
    }

    @ApiOperation("删除模型")
    @PostMapping("delete")
    public ReturnWrapper delete(@RequestParam("modelId") String modelId) {
        wfModelService.delete(modelId);
        return ReturnWrapMapper.ok();
    }

}
