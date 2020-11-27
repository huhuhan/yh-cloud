package com.yh.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class YhCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudGatewayApplication.class, args);
    }

    /**
     * 路由转发，代码实现方式，主要还是以yaml配置为主
     *
     * @param builder
     * @return org.springframework.cloud.gateway.route.RouteLocator
     * @author yanghan
     * @date 2019/6/20
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route((p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80")))
                .build();
    }

}
