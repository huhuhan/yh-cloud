package com.yh.cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 资源服务的配置
 * @EnableResourceServer 表示开启资源服务，参考注解源码（ResourceServerConfiguration，会替代WebSecurityConfigurerAdapter，因为order小）
 * @author yanghan
 * @date 2019/7/9
 */
@EnableResourceServer
@SpringBootApplication
public class YhCloudAppDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudAppDemoApplication.class, args);
    }

}
