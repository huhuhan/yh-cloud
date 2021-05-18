package com.yh.cloud.auth.config;

import com.yh.common.auth.config.DefaultWebSecurityConfig;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * security安全配置
 * 主要用默认的认证管理，其他用资源服务器配置为主
 *
 * @author yanghan
 * @date 2021/1/27
 */
@EnableWebSecurity
public class WebSecurityConfig extends DefaultWebSecurityConfig {
}