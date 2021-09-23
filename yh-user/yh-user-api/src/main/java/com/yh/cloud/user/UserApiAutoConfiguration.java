package com.yh.cloud.user;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用户服务的接口包，自动注入
 * @author yanghan
 * @date 2021/3/26
 */
@EnableFeignClients("com.yh.cloud.user")
@Configuration
@ComponentScan("com.yh.cloud.user")
public class UserApiAutoConfiguration {

}
