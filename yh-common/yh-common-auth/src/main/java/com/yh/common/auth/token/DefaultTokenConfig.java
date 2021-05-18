package com.yh.common.auth.token;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Token的配置
 *
 * @author yanghan
 * @date 2019/7/9
 */
@ConditionalOnProperty(prefix = "yh.oauth2.token", name = "store", havingValue = "memory", matchIfMissing = true)
public class DefaultTokenConfig {

    @Bean
    public TokenStore inMemoryTokenStore() {
        //配置token缓存器,采用spring默认的内存缓存器
        return new InMemoryTokenStore();
    }

}
