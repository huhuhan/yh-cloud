package com.yh.cloud.sys;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统通用服务
 * @author yanghan
 * @date 2021/9/13
 */
@EnableSwagger2Doc
@SpringBootApplication
public class YhCloudSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudSysApplication.class, args);
    }

}
