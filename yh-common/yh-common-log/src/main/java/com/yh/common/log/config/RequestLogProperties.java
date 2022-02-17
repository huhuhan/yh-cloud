package com.yh.common.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;


/**
 *
 * @author yanghan
 * @date 2022/2/15
 */
@Data
@ConfigurationProperties(prefix = RequestLogProperties.PREFIX)
public class RequestLogProperties {
    public static final String PREFIX = "yh.request-log";

    /** 是否开启 */
    private Boolean enabled = true;

    /**
     * 字段长度限制；-1 小于1不做任何限制
     */
    private Integer fieldLogLength = -1;

    /**
     * 脱敏字段
     */
    private Set<String> sensitiveFields;

    /**
     * 忽略地址
     */
    private String[] ignoreUrls;
}
