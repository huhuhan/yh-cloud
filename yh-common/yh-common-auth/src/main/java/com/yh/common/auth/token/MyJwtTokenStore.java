package com.yh.common.auth.token;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/2/2
 */
@ConditionalOnProperty(prefix = "yh.oauth2.token", name = "store", havingValue = "jwt")
public class MyJwtTokenStore {

    @Resource
    private TokenEnhancerInfoService tokenEnhancerInfoService;

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 令牌转化器，即token的秘钥，由jdk自带keytool.exe生成
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(this.keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("yh.jks"), "yh-cloud".toCharArray());
//        return keyStoreKeyFactory.getKeyPair("yh", "yh-cloud".toCharArray());

        // 对照：yh-auth/yh-auth-server/src/main/resources/keytool.text
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("yh-cloud.jks"), "yh-admin".toCharArray());
        return keyStoreKeyFactory.getKeyPair("yh-jwt", "yh-user".toCharArray());
    }

    /**
     * 令牌增强器，即token添加额外信息
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return (accessToken, authentication) -> {

            Map<String, Object> info = new HashMap<>(tokenEnhancerInfoService.getTokenInfo(authentication));

            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        };
    }

}
