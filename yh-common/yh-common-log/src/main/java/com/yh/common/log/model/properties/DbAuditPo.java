package com.yh.common.log.model.properties;

import com.zaxxer.hikari.HikariConfig;

/**
 * 日志数据源配置
 * logType=db时生效(非必须)，如果不配置则使用当前数据源
 *
 * @author yanghan
 * @date 2022/1/4
 */
public class DbAuditPo extends HikariConfig {
}
