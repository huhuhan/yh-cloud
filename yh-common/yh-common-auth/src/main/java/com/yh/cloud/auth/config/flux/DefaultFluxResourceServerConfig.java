package com.yh.cloud.auth.config.flux;

import com.yh.cloud.auth.properties.AuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * 认证、鉴权服务器
 *
 * @author yanghan
 * @date 2020/12/8
 */

@Import(DefaultServerSecurityConfig.class)
public class DefaultFluxResourceServerConfig {
    @Resource
    private AuthProperties authProperties;
    @Resource
    private ServerAccessDeniedHandler fluxAccessDeniedHandler;
    @Resource
    private ServerAuthenticationEntryPoint fluxAuthenticationEntryPoint;
    @Resource
    private ReactiveAuthorizationManager authorizationManager;
    @Resource
    private ReactiveAuthenticationManager authenticationManager;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // 资源保护认证
        http.oauth2ResourceServer()
                .authenticationManagerResolver(serverHttpRequest -> Mono.just(authenticationManager))
                .accessDeniedHandler(fluxAccessDeniedHandler)
                .authenticationEntryPoint(fluxAuthenticationEntryPoint)
        ;

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchange = http.authorizeExchange();
        // 白名单配置
        if (authProperties.getAuthIgnore().length > 0) {
            authorizeExchange.pathMatchers(authProperties.getAuthIgnore()).permitAll();
        }
        authorizeExchange
                //鉴权管理器
                .anyExchange().access(authorizationManager)
                .and()
                .headers().frameOptions().disable()
                .and()
                .httpBasic().disable()
                .csrf().disable();

        return http.build();
    }

}
