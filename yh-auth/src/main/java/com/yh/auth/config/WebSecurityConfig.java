package com.yh.auth.config;

import com.yh.auth.handler.MyAuthenticationFailureHandler;
import com.yh.auth.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 *
 * @author yanghan
 * @date 2019/7/8
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    /**
     * 实例化认证管理对象，给oauth2用
     *
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * http安全配置
     *
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置授权请求
        http.authorizeRequests()
                //允许所有oauth2请求访问，参考源码TokenEndpoint
                .antMatchers("/oauth/**").permitAll()
                //允许自定义的接口
                .antMatchers("/", "/*.ico").permitAll()
                //任何都要认证（不包括以上匹配的）
                .anyRequest().authenticated()
                //开启拦截后重定向到登录界面，自带/login页面，参考源码
                .and().formLogin()
//                .successHandler(myAuthenticationSuccessHandler)
//                .failureHandler(myAuthenticationFailureHandler)
//                .and().logout()
//                .and()
//                .httpBasic()
                //security默认开启csrf认证，将其关闭
                .and().csrf().disable();
    }

}
