package com.yh.common.auth.handler.flux;

import com.yh.common.auth.util.TempUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 未登录访问
 *
 * @author yanghan
 * @date 2020/12/8
 */
//@Component
public class FluxAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.valueOf(HttpStatus.UNAUTHORIZED.value()));
        String msg = "未登陆或令牌失效，请重新登录!";
        return TempUtils.responseWriter(response,
                TempUtils.tempResult(HttpStatus.UNAUTHORIZED.value(), msg, e.getMessage(), null));
    }
}
