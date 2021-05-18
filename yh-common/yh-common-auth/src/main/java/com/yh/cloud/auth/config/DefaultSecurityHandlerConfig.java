package com.yh.cloud.auth.config;

import com.yh.cloud.auth.handler.MyAccessDeniedHandler;
import com.yh.cloud.auth.handler.MyLoginUrlAuthenticationEntryPoint;
import com.yh.cloud.auth.handler.MyLogoutHandler;
import com.yh.cloud.auth.handler.MyLogoutSuccessHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * security异常处理类
 *
 * @author yanghan
 * @date 2021/1/26
 */
public class DefaultSecurityHandlerConfig {

    /**
     * 未登录访问
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new MyLoginUrlAuthenticationEntryPoint();
    }

    /**
     * 已登录但无权访问
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    /**
     * oauth2表达式
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }


    /**
     * 退出登录
     *
     * @return
     */
    @Bean
    public LogoutHandler logoutHandler() {
        return new MyLogoutHandler();
    }

    /**
     * 成功退出登录
     *
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new MyLogoutSuccessHandler();
    }
}
