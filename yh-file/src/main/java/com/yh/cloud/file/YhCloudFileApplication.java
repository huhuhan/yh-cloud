package com.yh.cloud.file;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件系统服务
 * @author yanghan
 * @date 2021/9/13
 */
@EnableSwagger2Doc
@SpringBootApplication
public class YhCloudFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(YhCloudFileApplication.class, args);
    }

}
