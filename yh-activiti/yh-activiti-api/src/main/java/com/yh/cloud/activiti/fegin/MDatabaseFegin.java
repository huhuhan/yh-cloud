package com.yh.cloud.activiti.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测试
 * 同名服务，多个类，需要加contextId，bean初始化不能同名
 *
 * @author yanghan
 * @date 2019/10/31
 */

@FeignClient(name = "YH-ACTIVITI5", contextId = "database")
public interface MDatabaseFegin {
    @GetMapping("/management/database/list")
    Object getDatabaseList(@RequestParam(required = false) String tableName,
                           @RequestParam(defaultValue = "1000") Integer pageSize,
                           @RequestParam(defaultValue = "0") Integer pageNum);
}
