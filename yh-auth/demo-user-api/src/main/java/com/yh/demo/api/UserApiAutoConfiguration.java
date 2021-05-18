package com.yh.demo.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用户服务的接口包，自动注入
 * @author yanghan
 * @date 2021/3/26
 */
@EnableFeignClients("com.yh.demo.api")
@Configuration
@ComponentScan("com.yh.demo.api")
public class UserApiAutoConfiguration {

}
