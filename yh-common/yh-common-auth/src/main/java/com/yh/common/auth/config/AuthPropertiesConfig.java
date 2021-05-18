package com.yh.common.auth.config;

import com.yh.common.auth.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * 注入配置属性
 *
 * @author yanghan
 * @date 2021/1/26
 */
@EnableConfigurationProperties(AuthProperties.class)
public class AuthPropertiesConfig {
}
