package com.yh.cloud.user;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户组织服务
 * @author yanghan
 * @date 2021/9/13
 */
@EnableSwagger2Doc
@SpringBootApplication
public class YhCloudUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudUserApplication.class, args);
    }

}
