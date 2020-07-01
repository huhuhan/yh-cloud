package com.yh.cloud.demo.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 * 配置自定义的WebSecurityConfig，@EnableOAuth2Sso需要放在一起
 * 因为@EnableOAuth2Sso包含默认的OAuth2SsoDefaultConfiguration，即覆盖掉
 *
 * @author yanghan
 * @date 2019/7/8
 */
@EnableOAuth2Sso
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * http安全配置
     *
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加默认注销接口/logout
        http.
                logout()
                //同时退出认证服务器的cookie登录，todo
                .logoutSuccessUrl("http://localhost:9001/logout")
                .and()
                //设置授权请求
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

}
