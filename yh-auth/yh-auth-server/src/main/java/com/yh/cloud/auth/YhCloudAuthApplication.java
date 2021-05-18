package com.yh.cloud.auth;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证、授权服务中心
 * @author yanghan
 * @date 2019/7/2
 */
@EnableSwagger2Doc
@SpringBootApplication
public class YhCloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudAuthApplication.class, args);
    }

}
