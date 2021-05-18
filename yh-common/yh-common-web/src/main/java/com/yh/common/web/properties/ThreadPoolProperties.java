package com.yh.common.web.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 线程池配置
 * @author yanghan
 * @date 2021/3/17
 */
@ConfigurationProperties(prefix = "yh.thread-pool")
@Setter
@Getter
//todo ：刷新机制
//@RefreshScope
public class ThreadPoolProperties {

    /** 核心线程数 */
    private Integer corePoolSize = 10;

    /** 最大线程数 */
    private Integer maxPoolSize = 200;

    /** 队列最大长度 */
    private Integer queueCapacity = 10;

    /** 线程池前缀 */
    private String threadNamePrefix = "YHExecutor-";

    /** 线程存活时间，单位秒 */
    private Integer keepAliveSeconds = 60;

    /** 拒绝策略 */
    private String rejectPolicy = "callerRuns";
}
