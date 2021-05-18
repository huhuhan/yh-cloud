package com.yh.cloud.auth.config.flux;

import com.yh.cloud.auth.handler.flux.FluxAccessDeniedHandler;
import com.yh.cloud.auth.handler.flux.FluxAuthenticationEntryPoint;
import com.yh.cloud.auth.manager.flux.FluxAuthenticationManager;
import com.yh.cloud.auth.manager.flux.FluxAuthorizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

/**
 * security异常处理类
 * webflux版本
 *
 * @author yanghan
 * @date 2021/1/26
 */
public class DefaultServerSecurityConfig {

    /**
     * 未登录访问
     *
     * @return
     */
    @Bean
    public ServerAuthenticationEntryPoint fluxAuthenticationEntryPoint() {
        return new FluxAuthenticationEntryPoint();
    }

    /**
     * 已登录但无权访问
     *
     * @return
     */
    @Bean
    public ServerAccessDeniedHandler fluxAccessDeniedHandler() {
        return new FluxAccessDeniedHandler();
    }


    /**
     * 鉴权管理器
     *
     * @return
     */
    @Bean
    public ReactiveAuthorizationManager fluxAuthorizationManager() {
        return new FluxAuthorizationManager();
    }

    /**
     * 认证管理器
     *
     * @return
     */
    @Bean
    public ReactiveAuthenticationManager fluxAuthenticationManager() {
        return new FluxAuthenticationManager();
    }
}
