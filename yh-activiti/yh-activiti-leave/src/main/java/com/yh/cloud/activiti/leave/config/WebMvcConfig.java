package com.yh.cloud.activiti.leave.config;

import com.yh.cloud.web.resolver.CurrentUserArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * webmvc配置
 *
 * @author yanghan
 * @date 2019/6/21
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加解析器
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //当前登录用户对象
        resolvers.add(new CurrentUserArgumentResolver());
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //测试
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("logger|请求路径：{}", request.getRequestURI());
                return true;
            }
        });
//        registry.addInterceptor(new JwtAuthenticationInterceptor())
//                .excludePathPatterns("/actuator/info",
//                        "/swagger-ui.html/**",
//                        "/webjars/**",
//                        "/swagger-resources/**",
//                        "/error");
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
        //文件服务映射
//        registry.addResourceHandler(PathUtil.serverPath + "/**")
//                .addResourceLocations("file:" + PathUtil.uploadDir + "/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
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

}
