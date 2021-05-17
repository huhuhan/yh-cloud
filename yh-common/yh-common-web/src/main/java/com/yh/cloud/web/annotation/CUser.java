package com.yh.cloud.web.annotation;

import java.lang.annotation.*;

/**
 * <p>绑定当前登录的用户</p>
 * <p>不同于@ModelAttribute</p>
 * @author yanghan
 * @date 2019/6/28
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CUser {
    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default "currentUser";

}
