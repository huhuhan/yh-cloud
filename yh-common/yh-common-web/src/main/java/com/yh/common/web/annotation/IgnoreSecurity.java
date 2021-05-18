package com.yh.common.web.annotation;

import java.lang.annotation.*;

/**
 * 忽略认证，接口、参数注解
 * @author yanghan
 * @date 2019/6/28
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSecurity {

    boolean validate() default false;
}
