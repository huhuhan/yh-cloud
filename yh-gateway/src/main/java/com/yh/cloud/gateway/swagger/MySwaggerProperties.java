package com.yh.cloud.gateway.swagger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * swagger服务组
 *
 * @author yanghan
 * @date 2020/12/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "yh.swagger")
public class MySwaggerProperties {
    private Map<String, List<String>> groups;
    private List<String> ignore;
}
