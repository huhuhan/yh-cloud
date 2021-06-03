package com.yh.cloud.generator.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author yanghan
 * @date 2021/6/2
 */
@Configuration
@MapperScan(value = {"com.yh.cloud.*.mapper"})
public class MybatisPlusConfig {
}