package com.yh.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 单点登录客户端
 * @EnableOAuth2Sso
 * 包含默认WebSecurityConfigurerAdapter，即OAuth2SsoDefaultConfiguration类
 */
@EnableOAuth2Sso
@SpringBootApplication
public class YhCloudAppDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudAppDemo2Application.class, args);
    }

}
