package com.yh.common.auth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 扩展授权模式（5种）配置
 * 参考源码 {@link AuthorizationServerEndpointsConfigurer}
 *
 * @author yanghan
 * @date 2021/6/16
 */
public class TokenGrantConfig {
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore tokenStore;

    /** jwtToken的转化器 */
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /** jwtToken自定义扩展对象 */
    @Autowired
    @Qualifier("jwtTokenEnhancer")
    private TokenEnhancer jwtTokenEnhancer;

    /** authorization_code模式的生成接口 */
    private AuthorizationCodeServices authorizationCodeServices;

    /** 刷新令牌支持更新 */
    private static final boolean REUSE_REFRESH_TOKEN = true;

    /**
     * 必须自定义，SpringBean初始化顺序有关
     * tokenGranter必须优先于AuthorizationServerConfigurerAdapter，否则配置无效还是用源配置加载的4种授权模式。
     */
    private AuthorizationServerTokenServices tokenServices;

    /**
     * 自定义令牌授权器，扩展授权模式
     *
     * @return
     */
    @Bean
    public TokenGranter tokenGranter() {
        return new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (this.delegate == null) {
                    this.delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                }
                return this.delegate.grant(grantType, tokenRequest);
            }
        };
    }

    /**
     * 源码方法，扩展授权模式
     *
     * @return
     */
    private List<TokenGranter> getDefaultTokenGranters() {
        ClientDetailsService clientDetails = clientDetailsService;
        AuthorizationServerTokenServices tokenServices = this.tokenServices();
        AuthorizationCodeServices authorizationCodeServices = this.authorizationCodeServices();
        OAuth2RequestFactory requestFactory = this.requestFactory();
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 授权码模式 authorization_code
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails, requestFactory));
        // 刷新令牌模式 refresh_token
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        // 隐式模式 implicit
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory));
        // 客户端模式 client_credentials
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (this.authenticationManager != null) {
            // 用户密码模式 password
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(this.authenticationManager, tokenServices, clientDetails, requestFactory));
        }
        // todo： 扩展补充其他授权码方法，比如验证码、手机号、第三方应用等等
        return tokenGranters;
    }

    /**
     * 源码方法
     * 授权码生成接口，后期可以扩展
     *
     * @return
     */
    private AuthorizationCodeServices authorizationCodeServices() {
        if (this.authorizationCodeServices == null) {
            this.authorizationCodeServices = new InMemoryAuthorizationCodeServices();
        }
        return this.authorizationCodeServices;
    }

    /**
     * 源码方法
     *
     * @return
     */
    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    /**
     * 源码方法
     * 令牌生成接口
     *
     * @return
     */
    private AuthorizationServerTokenServices tokenServices() {
        if (this.tokenServices != null) {
            return this.tokenServices;
        } else {
            this.tokenServices = this.createDefaultTokenServices();
            return this.tokenServices;
        }
    }

    /**
     * 源码方法
     *
     * @return
     */
    private DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(REUSE_REFRESH_TOKEN);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        addUserDetailsService(tokenServices, this.userDetailsService);
        return tokenServices;
    }

    /**
     * 源码方法，修改为jwtToken增强器
     *
     * @return
     */
    private TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        return enhancerChain;
    }

    /**
     * 源码方法
     *
     * @return
     */
    private void addUserDetailsService(DefaultTokenServices tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper(userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        }

    }
}
