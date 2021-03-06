package com.yh.cloud.auth.config;

import com.yh.common.auth.token.AuthorizationCodeServicesConfig;
import com.yh.common.auth.token.TokenGrantConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器配置
 *
 * @author yanghan
 * @date 2019/7/8
 */
@Configuration
@EnableAuthorizationServer
@Import({TokenGrantConfig.class, AuthorizationCodeServicesConfig.class})
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /** 申明认证对象，采用spring security的认证对象 */
    @Autowired
    private AuthenticationManager authenticationManager;

    /** spring提供的BCrypt加密 */
    @Autowired
    private PasswordEncoder passwordEncode;

    /** TokenStore生成器，仅能存在一个实例根据@Primary选择，参考源码实现类 */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private TokenGranter tokenGranter;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    /**
     * 授权服务安全认证的配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //设置/oauth/token_key可以无权访问
                .tokenKeyAccess("permitAll()")
                //设置/oauth/check_token可以无权访问
                .checkTokenAccess("permitAll()")
                //支持client_id以及client_secret作参数认证，参考源码，即开启clientCredentialsTokenEndpointFilter过滤器
                //否则client_id以及client_secret只能basic auth加密请求认证，实例在postman上
                .allowFormAuthenticationForClients();
    }


    /**
     * 客户端信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从内存中加载客户端信息
        clients.inMemory()
                //客户端ID，必填
                .withClient("client_demo")
                //客户端秘钥, security5.0后要求秘钥加密
                .secret(passwordEncode.encode("secret_client_demo"))
                //作用域
                .scopes("all")
                //支持授权客户端的权限类型,共5种
                .authorizedGrantTypes(AuthorizationGrantType.IMPLICIT.getValue(),
                        AuthorizationGrantType.AUTHORIZATION_CODE.getValue(),
                        AuthorizationGrantType.REFRESH_TOKEN.getValue(),
                        AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(),
                        AuthorizationGrantType.PASSWORD.getValue())
                //授权客户端的权限
                .authorities("client_x")
                //demo：authorization_code方式，需要重定向地址参数，响应的code验证码会加载该地址后面
                .redirectUris("http://www.baidu.com")
                //刷新token时效，秒
                .refreshTokenValiditySeconds(3600)
                //访问token时效，秒
                .accessTokenValiditySeconds(3600)
        ;
        //实现类的方式加载，从数据库存储客户端信息
//        clients.withClientDetails(MyClientDetailService)
    }

    /**
     * 授权服务器token的配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 要保证创建的token和验证的token来自同个TokenStore
        endpoints.tokenStore(tokenStore);
        /**
         * 默认支持4种模式，添加后authenticationManager才支持password模式
         * 参考 {@link AuthorizationServerEndpointsConfigurer 的getDefaultTokenGranters()方法 }
         */
        endpoints.authenticationManager(authenticationManager);

        // 自定义授权模式（5+N）
        endpoints.tokenGranter(tokenGranter);
        endpoints.authorizationCodeServices(authorizationCodeServices);
    }

    @Bean
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            private Logger log = LoggerFactory.getLogger(getClass());

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                //异常堆栈信息输出
                log.error("异常堆栈信息", e);
                return super.translate(e);
            }
        };
    }


}
