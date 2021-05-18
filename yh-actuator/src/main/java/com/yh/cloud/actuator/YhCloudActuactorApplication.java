package com.yh.cloud.actuator;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务监控
 * @author yanghan
 * @date 2021/3/25
 */
@EnableAdminServer
@SpringBootApplication
public class YhCloudActuactorApplication {
    public static void main(String[] args) {
        SpringApplication.run(YhCloudActuactorApplication.class, args);
    }
}
