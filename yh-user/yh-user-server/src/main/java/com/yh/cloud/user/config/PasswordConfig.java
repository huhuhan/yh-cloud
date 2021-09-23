package com.yh.cloud.user.config;

import com.yh.common.web.config.DefaultPasswordConfig;
import com.yh.common.web.crypto.MyPasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码配置
 *
 * @author yanghan
 * @date 2021/1/27
 */
@Configuration
public class PasswordConfig extends DefaultPasswordConfig {

    @Override
    public PasswordEncoder passwordEncoder() {
        // 需要与yh-auth-server的passwordEncoder实现方式相同
        return new MyPasswordEncoder();
    }

}
