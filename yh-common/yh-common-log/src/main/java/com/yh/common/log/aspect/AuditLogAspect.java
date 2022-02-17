package com.yh.common.log.aspect;

import com.yh.common.log.annotation.AuditLog;
import com.yh.common.log.config.AuditLogProperties;
import com.yh.common.log.model.entity.Audit;
import com.yh.common.log.service.IAuditService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 日志切面
 *
 * @author yanghan
 * @date 2022/1/4
 */
@Slf4j
@Aspect
public class AuditLogAspect {
    @Value("${spring.application.name}")
    private String applicationName;

    private final AuditLogProperties auditLogProperties;
    private final IAuditService auditService;

    public AuditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
        this.auditLogProperties = auditLogProperties;
        this.auditService = auditService;
    }

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Before("@within(auditLog) || @annotation(auditLog)")
    public void beforeMethod(JoinPoint joinPoint, AuditLog auditLog) throws Exception {
        //判断功能是否开启
        if (auditLogProperties.getEnabled()) {
            if (auditService == null) {
                log.warn("AuditLogAspect - auditService is null");
                return;
            }
            if (auditLog == null) {
                // 获取类上的注解
                auditLog = joinPoint.getTarget().getClass().getDeclaredAnnotation(AuditLog.class);
            }
            try {
                Audit audit = this.getAudit(auditLog, joinPoint);
                auditService.todo(audit);
            } catch (Exception e) {
                throw new Exception("请规范使用日志插件！", e);
            }
        }
    }

    private Audit getAudit(AuditLog auditLog, JoinPoint joinPoint) {
        Audit audit = new Audit();
        audit.setTimestamp(LocalDateTime.now());
        audit.setApplicationName(applicationName);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        audit.setClassName(methodSignature.getDeclaringTypeName());
        audit.setMethodName(methodSignature.getName());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userId = request.getHeader(auditLogProperties.getHeaderKey().getUserId());
        String userName = request.getHeader(auditLogProperties.getHeaderKey().getUserName());
        audit.setUserId(userId);
        audit.setUserName(userName);

        String operation = auditLog.operation();
        if (operation.contains("#")) {
            //获取方法参数值
            Object[] args = joinPoint.getArgs();
            operation = this.getValueBySpEL(operation, methodSignature, args);
        }
        audit.setOperation(operation);

        return audit;
    }

    /**
     * 解析spEL表达式
     */
    private String getValueBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEL);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }
}
