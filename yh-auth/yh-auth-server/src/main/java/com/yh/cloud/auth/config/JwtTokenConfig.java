package com.yh.cloud.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken的配置
 *
 * @author yanghan
 * @date 2019/7/9
 */
@Configuration
public class JwtTokenConfig {
    private static KeyPair KEY_PAIR;

    //此处只有在授权服务器和资源服务器在同个服务中的时候才能这样搞（在内存中），实际使用RSA还是需要用JDK或openssl去生成证书
    /*static {
        try {
            KEY_PAIR = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/

    @Bean
    @Primary
    public TokenStore jwtTokenStore() {
        //配置token缓存器,采用spring默认的内存缓存器
        //return new InMemoryTokenStore();
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public TokenStore inMemoryTokenStore() {
        //配置token缓存器,采用spring默认的内存缓存器
        return new InMemoryTokenStore();
    }

    /**
     * 令牌转化器，即token的秘钥，由jdk自带keytool.exe生成
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        /*JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setKeyPair(KEY_PAIR);
        return accessTokenConverter;*/
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("yh-cloud.jks"), "yh-admin".toCharArray()
        );
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("yh-jwt", "yh-user".toCharArray()));
        return converter;
    }

    /**
     * 暂不明确作用，注释不影响使用
     */
    @Bean
    public ResourceServerTokenServices resourceJwtTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        // 使用自定义的Token转换器
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        // 使用自定义的tokenStore
        defaultTokenServices.setTokenStore(jwtTokenStore());
        return defaultTokenServices;
    }

    /**
     * 令牌增强器，即jwt的token添加额外信息
     *
     * @return
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Authentication userAuthentication = authentication.getUserAuthentication();
                if (userAuthentication != null) {
                    String userName = userAuthentication.getName();
                    //数据库查询用户，加到token中
                    if (null != userName || true) {
                        Map<String, Object> additionalInformation = new HashMap<>();
                        Map<String, Object> map = new HashMap<>();
                        map.put("userName", "admin");
                        map.put("createTime", new Date().toString());
                        map.put("gender", "男");
                        additionalInformation.put("roles", Arrays.asList("admin", "person"));
                        additionalInformation.put("user", map);//GsonUtil.toJson(map));
                        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                    }
                }
                return accessToken;
            }
        };
    }
}
