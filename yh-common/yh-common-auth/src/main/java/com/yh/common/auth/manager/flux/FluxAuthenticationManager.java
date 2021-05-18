package com.yh.common.auth.manager.flux;

import com.yh.common.auth.constant.MyAuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 认证管理器
 * 参考源码{@link JwtReactiveAuthenticationManager}
 *
 * @author yanghan
 * @date 2021/1/29
 */
@Slf4j
public class FluxAuthenticationManager implements ReactiveAuthenticationManager {
    @Resource
    private ReactiveJwtDecoder jwtDecoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter((a) -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap(this.jwtDecoder::decode)
                .flatMap(this.reactiveJwtAuthenticationConverterAdapter()::convert)
                .cast(Authentication.class)
                .onErrorMap(JwtException.class, this::onError);
    }

    private OAuth2AuthenticationException onError(JwtException e) {
        OAuth2Error invalidRequest = invalidToken(e.getMessage());
        return new OAuth2AuthenticationException(invalidRequest, invalidRequest.getDescription(), e);
    }

    private static final OAuth2Error DEFAULT_INVALID_TOKEN = invalidToken("An error occurred while attempting to decode the Jwt: Invalid token");

    private static OAuth2Error invalidToken(String message) {
        try {
            return new BearerTokenError("invalid_token", HttpStatus.UNAUTHORIZED, message, "https://tools.ietf.org/html/rfc6750#section-3.1");
        } catch (IllegalArgumentException var2) {
            return DEFAULT_INVALID_TOKEN;
        }
    }


    /**
     * Jwt解析器
     * 默认转化器需要权限角色全称，和oauth2的不同
     *
     * @author yanghan
     * @date 2020/12/8
     */
    @Bean
    public ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverterAdapter() {
        // 修改默认转化器，设置自动添加权限角色前缀ROLE_和权限属性名authorities
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(MyAuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(MyAuthConstant.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
