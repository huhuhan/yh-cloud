package com.yh.cloud.web.resolver;

import com.yh.cloud.web.annotation.CUser;
import com.yh.cloud.web.entity.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


/**
 * Token解析为当前用户对象
 * 【HandlerMethodArgumentResolver】方法参数解析器
 *
 * @author yanghan
 * @date 2019/6/28
 */
@Slf4j
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 1. 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(CurrentUser.class)
                && methodParameter.hasParameterAnnotation(CUser.class);
    }

    /**
     * 入参处理
     *
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws MissingServletRequestPartException {
        //jwt拦截器封装了currentUser
//        CurrentUser user = (CurrentUser) nativeWebRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
//        if (user != null) {
//            return user;
//        }
//        throw new MissingServletRequestPartException("currentUser");

        //todo : Demo 用户
        CurrentUser user = new CurrentUser();
        user.setUserId(88888888L);
        user.setUserName("测试Demo管理员");
        user.setLoginName("adminDemo");
        return user;
    }

}
