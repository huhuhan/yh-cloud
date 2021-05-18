package com.yh.cloud.activiti.rest.management;

import com.yh.cloud.activiti.service.management.MProcessEngineInfoService;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Activitiy引擎信息管理
 *
 * @author yanghan
 * @date 2019/10/31
 */
@Api(tags = "Activiti引擎信息管理")
@RestController
@RequestMapping(value = "/management/engine")
public class MProcessEngineInfoController {

    @Autowired
    private MProcessEngineInfoService mProcessEngineInfoService;

    @ApiOperation("引擎+系统信息")
    @GetMapping("info")
    public ReturnWrapper info() {
        return ReturnWrapMapper.ok(mProcessEngineInfoService.info());
    }

}
