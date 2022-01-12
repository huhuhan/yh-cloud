package com.yh.common.log.annotation;

import java.lang.annotation.*;


/**
 * 日志注解
 * @author yanghan
 * @date 2022/1/4
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作信息
     * @author yanghan
     * @date 2022/1/4
     */
    String operation();
}
