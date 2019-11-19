package com.yh.cloud.activiti;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * 工作流服务
 *
 * @author yanghan
 * @date 2019/10/28
 */
@EnableSwagger2Doc
@EnableEurekaClient
@ComponentScan({"org.activiti.rest","com.yh.cloud.activiti"})
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableFeignClients
public class YhActiviti5Application {

    public static void main(String[] args) {
        SpringApplication.run(YhActiviti5Application.class, args);
    }

}
