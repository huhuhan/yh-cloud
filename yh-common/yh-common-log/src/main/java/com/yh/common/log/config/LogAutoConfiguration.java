package com.yh.common.log.config;

import com.yh.common.log.aspect.AuditLogAspect;
import com.yh.common.log.service.IAuditService;
import com.yh.common.log.service.impl.DbAuditServiceImpl;
import com.yh.common.log.service.impl.LoggerAuditServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * 自动化加载配置
 *
 * @author yanghan
 * @date 2021/12/28
 */
@Configuration
@EnableConfigurationProperties({AuditLogProperties.class})
@ConditionalOnProperty(
        name = AuditLogProperties.PREFIX + "." + "enabled",
        havingValue = "true"
)
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnClass({HttpServletRequest.class, RequestContextHolder.class})
    public AuditLogAspect auditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
        return new AuditLogAspect(auditLogProperties, auditService);
    }

    @Bean
    @ConditionalOnProperty(
            name = AuditLogProperties.PREFIX + "." + AuditLogProperties.LOG_TYPE,
            havingValue = "db"
    )
    @ConditionalOnClass(JdbcTemplate.class)
    public DbAuditServiceImpl dbAuditService(AuditLogProperties auditLogProperties, DataSource dataSource) {
        return new DbAuditServiceImpl(auditLogProperties, dataSource);
    }

    @Bean
    @ConditionalOnProperty(
            name = AuditLogProperties.PREFIX + "." + AuditLogProperties.LOG_TYPE,
            havingValue = "logger",
            matchIfMissing = true
    )
    public LoggerAuditServiceImpl loggerAuditService() {
        return new LoggerAuditServiceImpl();
    }

}
