package com.yh.common.log.config;

import com.yh.common.log.filter.RequestTraceFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghan
 * @date 2022/2/18
 */
@EnableConfigurationProperties(RequestTraceProperties.class)
@Configuration
public class RequestTraceAutoConfiguration {

    private final RequestTraceProperties requestTraceProperties;

    public RequestTraceAutoConfiguration(RequestTraceProperties requestTraceProperties) {
        this.requestTraceProperties = requestTraceProperties;
    }

    @Bean
    public FilterRegistrationBean<RequestTraceFilter> abRequestTranceFilterFilterRegistrationBean() {
        RequestTraceFilter requestTraceFilter = new RequestTraceFilter(requestTraceProperties.getIdName());
        return new FilterRegistrationBean<>(requestTraceFilter);
    }

}
