package com.yh.cloud.gateway.config;

import com.yh.common.auth.config.flux.DefaultFluxResourceServerConfig;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

/**
 * @author yanghan
 * @date 2021/2/1
 */
@EnableWebFluxSecurity
public class MyResourceServerConfig extends DefaultFluxResourceServerConfig {
}
