package com.yh.common.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanghan
 * @date 2022/2/18
 */
@Data
@ConfigurationProperties(prefix = RequestTraceProperties.PREFIX)
public class RequestTraceProperties {
    public static final String PREFIX = "yh.request-trace";

    private String idName = "traceId";
}
