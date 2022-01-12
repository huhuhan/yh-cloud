package com.yh.common.log.config;

import com.yh.common.log.model.properties.DbAuditPo;
import com.yh.common.log.model.properties.HeaderKeyPo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置
 *
 * @author yanghan
 * @date 2022/1/4
 */
@Data
@ConfigurationProperties(prefix = AuditLogProperties.PREFIX)
public class AuditLogProperties {
    public static final String PREFIX = "yh.audit-log";
    public static final String LOG_TYPE = "log-type";

    /** 是否开启 */
    private Boolean enabled = false;
    /** 日志记录类型(logger/db/) */
    private String logType;
    /** 请求头参数 */
    private HeaderKeyPo headerKey = new HeaderKeyPo();
    /** 数据源配置 */
    private DbAuditPo datasource;


    @ConfigurationProperties(prefix = AuditLogProperties.PREFIX + "." + "header-key")
    public HeaderKeyPo headerKeyPo() {
        return new HeaderKeyPo();
    }

    @ConfigurationProperties(prefix = AuditLogProperties.PREFIX + "." + "datasource")
    public DbAuditPo dbAuditPo() {
        return new DbAuditPo();
    }
}
