package com.yh.cloud.activiti.rest.management;

import com.yh.cloud.activiti.fegin.MDatabaseFegin;
import com.yh.cloud.activiti.service.management.MDatabaseService;
import com.yh.cloud.web.wrapper.ReturnWrapMapper;
import com.yh.cloud.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Activiti数据库管理
 *
 * @author yanghan
 * @date 2019/10/31
 */
@Api(tags = "Activiti数据库管理")
@RestController
@RequestMapping("/management/database")
public class MDatabaseController implements MDatabaseFegin {
    @Autowired
    private MDatabaseService mDatabaseService;

    @Autowired
    ManagementService managementService;

    @ApiOperation("列表")
    @GetMapping("list")
    @Override
    public ReturnWrapper getDatabaseList(@RequestParam(required = false) String tableName,
                                         @RequestParam(defaultValue = "1000") Integer pageSize,
                                         @RequestParam(defaultValue = "0") Integer pageNum) {
    return ReturnWrapMapper.ok(mDatabaseService.getDatabaseList(tableName, pageSize, pageNum));
    }
}
