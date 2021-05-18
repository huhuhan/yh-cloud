package com.yh.common.auth.handler.flux;

import com.yh.common.auth.util.TempUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 没有权限访问
 *
 * @author yanghan
 * @date 2020/12/8
 */
//@Component
public class FluxAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.valueOf(HttpStatus.FORBIDDEN.value()));
        String msg = "权限不足，不可访问，请联系管理员!";
        return TempUtils.responseWriter(response,
                TempUtils.tempResult(HttpStatus.FORBIDDEN.value(), msg, e.getMessage(), null));
    }
}
