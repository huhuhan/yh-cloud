package com.yh.common.log.config;

import com.yh.common.log.aspect.RequestAspect;
import com.yh.common.log.aspect.pointcut.RequestMappingHandlerPointcut;
import com.yh.common.log.service.CurrentUserLogService;
import com.yh.common.log.service.impl.DefaultCurrentUserLogServiceImpl;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghan
 * @date 2022/2/15
 */
@Configuration
@EnableConfigurationProperties(RequestLogProperties.class)
@ConditionalOnProperty(
        name = RequestLogProperties.PREFIX + "." + "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class RequestLogAutoConfiguration {

    private final RequestLogProperties requestLogProperties;

    public RequestLogAutoConfiguration(RequestLogProperties requestLogProperties) {
        this.requestLogProperties = requestLogProperties;
    }

    @Bean("currentUserLogService")
    @ConditionalOnMissingBean
    public CurrentUserLogService currentUserLogService() {
        return new DefaultCurrentUserLogServiceImpl();
    }


    @Bean(name = "requestPointcutAdvisor")
    public Advisor requestPointcutAdvisor() {
        RequestAspect requestAspect = new RequestAspect(requestLogProperties, this.currentUserLogService());
        requestAspect.afterPropertiesSet();
        return new DefaultPointcutAdvisor(new RequestMappingHandlerPointcut(), requestAspect);
    }
}
