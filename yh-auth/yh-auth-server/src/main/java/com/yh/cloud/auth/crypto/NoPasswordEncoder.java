package com.yh.cloud.auth.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 不加密
 * 扩展，实现PasswordEncoder，Spring Security 5要求密码使用PasswordEncoder加密
 * @author yanghan
 * @date 2020/5/29
 */
public class NoPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

}
