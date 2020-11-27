package com.yh.cloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关请求拦截
 *
 * @author yanghan
 * @date 2019/6/20
 */
@Slf4j
@Component
public class AuthSignFilter implements GlobalFilter, Ordered {

    /**
     * 网关拦截转发，主要方法
     * @author yanghan
     * @param exchange  即Predicate断言，设置规则
     * @param chain     即Filter过滤器，处理过滤
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @date 2020/11/26
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("网关拦截：" + exchange.getRequest().getURI());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
