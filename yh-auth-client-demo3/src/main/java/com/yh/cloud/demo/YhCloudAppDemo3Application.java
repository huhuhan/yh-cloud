package com.yh.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 单点登录客户端
 */
@SpringBootApplication
public class YhCloudAppDemo3Application {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudAppDemo3Application.class, args);
    }

}
