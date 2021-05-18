package com.yh.cloud.auth.properties;

import cn.hutool.core.util.ArrayUtil;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author yanghan
 * @date 2020/12/17
 */
@ConfigurationProperties(prefix = "yh.security")
@Setter
@NoArgsConstructor
@RefreshScope
public class AuthProperties {

    private String[] xssIgnore;
    private String[] csrfIgnore;
    private String[] authIgnore;

    public String[] getAuthIgnore() {
        return getStrings(authIgnore, AUTH_IGNORES);
    }

    public String[] getXssIgnore() {
        return getStrings(xssIgnore, XSS_IGNORES);
    }

    public String[] getCsrfIgnore() {
        return getStrings(csrfIgnore, CSRF_IGNORES);
    }

    private String[] getStrings(String[] ignoreArray, String[] defaultArray) {
        if (ignoreArray == null || ignoreArray.length == 0) {
            return defaultArray;
        }
        return ArrayUtil.addAll(defaultArray, ignoreArray);
    }

    /**
     * 默认白名单
     */
    private static final String[] AUTH_IGNORES = {
            "/rsa/publicKey",
            "/swagger-resources/**",
            "/v2/api-docs*",
            "/webjars/**",
            "/swagger-ui.html",
            "/actuator/**",
    };

    /**
     * 默认白名单
     */
    private static final String[] XSS_IGNORES = {
    };

    /**
     * 默认白名单
     */
    private static final String[] CSRF_IGNORES = {
            "127.0.0.1",
            "localhost",
    };

    public static final String[] TEST_URL = {
            "/auth/test-yh"
    };
}
