package com.yh.cloud.generator;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 代码生成器
 *
 * @author yanghan
 * @date 2021/6/2
 */
@EnableSwagger2Doc
@SpringBootApplication
public class YhCloudGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(YhCloudGeneratorApplication.class, args);
    }
}
