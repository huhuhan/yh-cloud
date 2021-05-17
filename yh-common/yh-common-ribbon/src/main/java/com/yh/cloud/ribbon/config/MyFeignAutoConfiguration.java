package com.yh.cloud.ribbon.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;


/**
 * Feign全局自动化配置
 * @author yanghan
 * @date 2021/3/23
 */
public class MyFeignAutoConfiguration {

    /**
     * 日志级别，优先级：全局代码级别，默认最低
     * <br> 操作方式，配置添加Logging.level下某XXClient的级别
     *
     * @return feign.Logger.Level
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
