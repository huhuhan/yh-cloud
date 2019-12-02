package com.yh.cloud.activiti.leave;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yanghan
 * @date 2019/12/2
 */
@EnableSwagger2Doc
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yh.cloud.activiti.fegin"})
public class YhCloudActivitiLeaveApplication {
    public static void main(String[] args) {
        SpringApplication.run(YhCloudActivitiLeaveApplication.class, args);
    }
}
