package com.yh.common.log.aspect.pointcut;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 请求切点，拦截所有请求
 * @author yanghan
 * @date 2022/2/17
 */
public class RequestMappingHandlerPointcut extends StaticMethodMatcherPointcut {


    private static final String FEIGN_CLIENT_CLASSNAME = "org.springframework.cloud.openfeign.FeignClient";

    /**
     * feign client class
     */
    private final Class<Annotation> feignClientAnnotationClass;

    public RequestMappingHandlerPointcut() {
        feignClientAnnotationClass = forFeignClientClassName();
    }

    @SuppressWarnings({"unchecked"})
    private Class<Annotation> forFeignClientClassName() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            return (Class<Annotation>) ClassUtils.forName(FEIGN_CLIENT_CLASSNAME, contextClassLoader);
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }

    private boolean containsAnnotation(Class<?> targetClass, Class<Annotation> feignClientAnnotationClass) {
        return targetClass.isAnnotationPresent(feignClientAnnotationClass)
                || Arrays.stream(targetClass.getInterfaces()).anyMatch(itemClass -> this.containsAnnotation(itemClass, feignClientAnnotationClass));
    }

    private boolean anyRequestMapping(Method method, Class<?> targetClass) {
        if (targetClass.isAnnotationPresent(RestController.class) || method.isAnnotationPresent(ResponseBody.class)) {
            return Objects.nonNull(AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class));
        }
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (Objects.nonNull(feignClientAnnotationClass) && containsAnnotation(targetClass, feignClientAnnotationClass)) {
            return false;
        }
        return this.anyRequestMapping(method, targetClass);
    }
}
