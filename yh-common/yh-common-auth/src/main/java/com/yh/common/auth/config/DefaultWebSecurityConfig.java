package com.yh.common.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Map;

/**
 * Security安全配置
 *
 * @author yanghan
 * @date 2019/7/8
 */
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        // 过滤器链直接忽略的路径
        web.ignoring().antMatchers("/auth/test");
    }

    /**
     * 实例化认证管理对象，建议采用默认生成，包括认证程序等系列的逻辑
     * 自己定义的话，可以参考AuthenticationManagerBuilder初始化
     *
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 参考方法
     *
     * @param http
     * @param handlerMap
     * @throws Exception
     */
    public void makeAccessHandler(HttpSecurity http, Map<String, Object> handlerMap) throws Exception {
        // 退出处理
        http.logout()
                .addLogoutHandler((LogoutHandler) handlerMap.get("logoutHandler"))
                .logoutSuccessHandler((LogoutSuccessHandler) handlerMap.get("logoutSuccessHandler"))
                // 清除上下文对象中的认证对象
                .clearAuthentication(true);

        // 异常处理
        http.exceptionHandling()
                //未登录无权访问异常处理
                .authenticationEntryPoint((AuthenticationEntryPoint) handlerMap.get("authenticationEntryPoint"))
                //已登录无权访问异常处理
                .accessDeniedHandler((AccessDeniedHandler) handlerMap.get("accessDeniedHandler"));

        // 禁用部分属性
        http.httpBasic().disable()
                .headers()
                .frameOptions().disable()
                .and()
                .csrf().disable();
    }
}
