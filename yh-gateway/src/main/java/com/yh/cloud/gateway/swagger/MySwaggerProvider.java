package com.yh.cloud.gateway.swagger;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 从eureka服务中心发现服务基层swagger文档
 *
 * @author yanghan
 * @date 2019/6/20
 */
@Component
@Primary
public class MySwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    @Autowired
    private final DiscoveryClientRouteDefinitionLocator routeLocator;
    @Value("${spring.application.name}")
    private String self;
    @Autowired
    private MySwaggerProperties mySwaggerProperties;

    public MySwaggerProvider(DiscoveryClientRouteDefinitionLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        //从DiscoveryClientRouteDefinitionLocator 中取出routes，构造成swaggerResource
        Map<String, List<String>> swaggerGroupMap = mySwaggerProperties.getGroups();
        Set<String> groupServers = swaggerGroupMap.keySet();
        List<String> ignore = mySwaggerProperties.getIgnore();
        routeLocator.getRouteDefinitions()
                .filter(routeDefinition -> !self.equalsIgnoreCase(routeDefinition.getUri().getHost()))
                .subscribe(routeDefinition -> {
                    String name = routeDefinition.getUri().getHost();
                    String lowerCaseName = name.toLowerCase();
                    String location = routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI);
                    if (ignore.contains(lowerCaseName)) {
                        return;
                    }
                    if (groupServers.contains(lowerCaseName)) {
                        for (String group : swaggerGroupMap.get(lowerCaseName)) {
                            resources.add(this.swaggerResource(name, location, group));
                        }
                    } else {
                        resources.add(this.swaggerResource(name, location, null));
                    }

                });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String group) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(null == group ? name : group.toUpperCase());
        swaggerResource.setLocation(this.swaggerLocation(location, group));
        swaggerResource.setSwaggerVersion("2.9.2");
        return swaggerResource;
    }

    private String swaggerLocation(String swaggerUrl, String swaggerGroup) {
        String base = Optional.of(swaggerUrl).get();
        return "default".equals(swaggerGroup) || null == swaggerGroup ? base : base + "?group=" + swaggerGroup;
    }
}
