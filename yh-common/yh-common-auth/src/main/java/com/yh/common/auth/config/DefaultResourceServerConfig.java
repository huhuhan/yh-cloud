package com.yh.common.auth.config;

import com.yh.common.auth.properties.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

/**
 * 资源服务器
 * 需要保护的服务，实现该类，添加注解@Configuration、@EnableResourceServer
 * HttpSecurity的配置优先级大于WebSecurity
 * @author yanghan
 * @date 2021/1/27
 */
@Import(DefaultSecurityHandlerConfig.class)
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {
    /** 业务服务作资源保存，没有tokenStore的话改用远程调用确认 */
    @Autowired(required = false)
    private TokenStore tokenStore;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Resource
    private AuthProperties authProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        if (null != tokenStore) {
            resources.tokenStore(tokenStore);
        }

        resources
                .authenticationEntryPoint(authenticationEntryPoint)
                .expressionHandler(expressionHandler)
                .accessDeniedHandler(accessDeniedHandler)
                // 默认session就是严格模式，即不会创建session，每次请求都要认证，和Security的默认配置有区别
                //.stateless(true)
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = this.setHttp(http)
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(authProperties.getAuthIgnore()).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(AuthProperties.TEST_URL).hasRole("YH")
                .anyRequest();
        this.setAuthenticate(authorizedUrl);

        // 禁用部分属性
        http.httpBasic().disable()
                .headers()
                .frameOptions().disable()
                .and()
                .csrf().disable();
    }

    /**
     * url权限控制，默认是认证就通过，可以重写实现个性化
     *
     * @param authorizedUrl
     */
    public HttpSecurity setAuthenticate(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl) {
        return authorizedUrl.authenticated().and();
    }

    /**
     * 留给子类重写扩展功能
     *
     * @param http
     */
    public HttpSecurity setHttp(HttpSecurity http) throws Exception {
        return http;
    }
}