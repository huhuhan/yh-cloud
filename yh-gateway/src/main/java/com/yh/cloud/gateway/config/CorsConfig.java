package com.yh.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 跨域配置
 *
 * @author yanghan
 * @date 2021/3/24
 */
@Configuration
public class CorsConfig {

    private static final String ALL = "*";

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public CorsWebFilter corsFilter2() {
        CorsConfiguration config = new CorsConfiguration();
        // cookie跨域
        config.setAllowCredentials(Boolean.TRUE);
        config.addAllowedMethod(ALL);
        config.addAllowedOrigin(ALL);
        config.addAllowedHeader(ALL);
        // 配置前端js允许访问的自定义响应头
        config.addExposedHeader("setToken");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
