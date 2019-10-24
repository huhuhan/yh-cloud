package com.yh.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心
 * @author yanghan
 * @date 2019/7/1
 */
@EnableEurekaServer
@SpringBootApplication
public class YhCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudEurekaApplication.class, args);
    }

}
