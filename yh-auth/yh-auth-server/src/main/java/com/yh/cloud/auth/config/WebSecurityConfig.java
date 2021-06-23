package com.yh.cloud.auth.config;

import com.yh.common.auth.config.DefaultWebSecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * security安全配置
 * 主要为了初始化Security的认证管理实例 {@link AuthenticationManager}
 * 其他配置，都在 {@link ResourceServerConfig}
 *
 * @author yanghan
 * @date 2021/1/27
 */
@EnableWebSecurity
public class WebSecurityConfig extends DefaultWebSecurityConfig {
}