package com.yh.common.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密类，Spring5开始要求加密
 * @author yanghan
 * @date 2021/1/26
 */
public class DefaultPasswordConfig {
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 官方默认提供加密方式，可参考修改
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
