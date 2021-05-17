package com.yh.cloud.web.config;

import com.yh.cloud.web.annotation.ApiRestController;
import com.yh.cloud.web.annotation.TestApiRestController;
import com.yh.cloud.web.properties.ApiPathProperties;
import com.yh.cloud.web.resolver.CurrentUserArgumentResolver;
import com.yh.cloud.web.service.ICurrentUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * webmvc配置
 *
 * @author yanghan
 * @date 2019/6/21
 */
@Slf4j
@EnableConfigurationProperties(ApiPathProperties.class)
public class DefaultWebMvcConfig implements WebMvcConfigurer {
    @Lazy
    @Resource
    private ICurrentUserService iCurrentUserService;
    @Lazy
    @Resource
    private ApiPathProperties apiPathProperties;

    /**
     * 添加解析器
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        /**
         *  详情解析方式查看CurrentUserArgumentResolver.resolveArgument()方法
         */
        resolvers.add(new CurrentUserArgumentResolver(iCurrentUserService));
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("请求路径|{}", request.getRequestURI());
                return true;
            }
        });
    }

    /**
     * 消息转化器
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/csrf");
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.APPLICATION_XML);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(apiPathProperties.getGlobalPrefix(), p -> p.isAnnotationPresent(ApiRestController.class));
        configurer.addPathPrefix(apiPathProperties.getTestPrefix(), p -> p.isAnnotationPresent(TestApiRestController.class));
    }
}
