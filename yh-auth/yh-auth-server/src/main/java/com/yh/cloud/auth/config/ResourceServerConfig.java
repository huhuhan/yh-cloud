package com.yh.cloud.auth.config;

import com.yh.common.auth.config.DefaultResourceServerConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 资源服务器安全配置
 * @author yanghan
 * @date 2021/1/27
 */
@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfig extends DefaultResourceServerConfig {
    private final LogoutHandler logoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;

    @Override
    public HttpSecurity setHttp(HttpSecurity http) throws Exception {
        // 退出处理
        http.logout()
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler)
                // 清除上下文对象中的认证对象
                .clearAuthentication(true);


        // 授权模式必须先登录且需要session，开启session的创建
        // 这里暂用Security自带登录页面
        //  todo：不需要授权模式就注释掉，session的创建会占用内存
        http.formLogin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        return http;
    }
}