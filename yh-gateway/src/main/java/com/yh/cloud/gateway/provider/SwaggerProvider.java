package com.yh.cloud.gateway.provider;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * 从eureka服务中心发现服务基层swagger文档
 *
 * @author yanghan
 * @date 2019/6/20
 */
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    private final DiscoveryClientRouteDefinitionLocator routeLocator;
    @Value("${spring.application.name}")
    private String self;

    public SwaggerProvider(DiscoveryClientRouteDefinitionLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        //从DiscoveryClientRouteDefinitionLocator 中取出routes，构造成swaggerResource
        routeLocator.getRouteDefinitions()
                //过滤网关的swagger
                .filter(routeDefinition -> !self.equalsIgnoreCase(routeDefinition.getUri().getHost()))
                .subscribe(routeDefinition -> {
                    resources.add(swaggerResource(routeDefinition.getUri().getHost(), routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI)));
                });

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.9.2");
        return swaggerResource;
    }
}
