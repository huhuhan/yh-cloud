package com.yh.cloud.web.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 接口前缀地址，对应自定义注解
 *
 * @author yanghan
 * @date 2021/4/27
 */
@ConfigurationProperties(prefix = "yh.api.path")
@Setter
@Getter
public class ApiPathProperties<T> {

    /** 全局注解，对应 {@link com.yh.cloud.web.annotation.ApiRestController} */
    private String globalPrefix = "yh";

    /** 测试专用 */
    private String testPrefix = "test";

    /** 扩展前缀 */
    private Map<String, String> diyPrefix;
}