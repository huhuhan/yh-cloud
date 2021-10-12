package com.yh.cloud.sys.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghan
 * @date 2021/9/13
 */
@Configuration
@MapperScan(value = {"com.yh.cloud.sys.mapper"})
public class MyBatisPlusConfig {
}
