package com.yh.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import com.yh.cloud.base.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 网关请求拦截
 *
 * @author yanghan
 * @date 2019/6/20
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 网关拦截转发，主要方法
     *
     * @param exchange 即Predicate断言，设置规则
     * @param chain    即Filter过滤器，处理过滤
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @author yanghan
     * @date 2020/11/26
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("网关转发：" + exchange.getRequest().getURI());

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }
        if (token.contains(OAuth2AccessToken.BEARER_TYPE)) {
            try {
                //从token中解析用户信息并设置到Header中去
                String realToken = token.replace(OAuth2AccessToken.BEARER_TYPE + " ", "");
                JWSObject jwsObject = JWSObject.parse(realToken);
                JSONObject playObj = jwsObject.getPayload().toJSONObject();
                // token 解析当前对象，当前服务需要
//                String currentUserStr = playObj.getAsString("currentUser");
//                if (!StringUtils.isEmpty(currentUserStr)) {
//                    CurrentUser currentUser = JSONUtil.toBean(currentUserStr, CurrentUser.class);
//                    exchange.getAttributes().put("currentUser", currentUser);
//                }
                // token解析属性，转发给其他服务，不用再解析
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header(BaseConstant.HEADER_CURRENT_USER, playObj.getAsString("currentUser"))
                        .header(BaseConstant.HEADER_CURRENT_USER_ID, playObj.getAsString("userId"))
                        .header(BaseConstant.HEADER_CURRENT_TENANT_ID, playObj.getAsString("tenantId"))
                        .build();
                exchange = exchange.mutate().request(request).build();
            } catch (Exception e) {
                log.error("AuthGlobalFilter, 解析TOKEN失败，{}", e.getMessage());
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
