package com.yh.common.auth.token;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * 授权模式，随机码存储对象
 * @author yanghan
 * @date 2021/6/23
 */
public class AuthorizationCodeServicesConfig {

    /**
     * 采用系统默认的内存方式，自定义则实现抽象类 {@link RandomValueAuthorizationCodeServices}
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new InMemoryAuthorizationCodeServices();
    }
}
