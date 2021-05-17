package com.yh.cloud.web.config;

import com.yh.cloud.web.properties.ThreadPoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yanghan
 * @date 2021/3/17
 */
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class DefaultAsyncTaskConfig {

    @Bean
    public TaskExecutor taskExecutor(ThreadPoolProperties threadPoolProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        switch (threadPoolProperties.getRejectPolicy()) {
            case "callerRuns":
                // 拒绝策略，满队列后，由调用者线程完成
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
                break;
            case "discard":
                // 拒绝策略，满队列后，拒绝但不抛出异常
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
                break;
            case "discardOldest":
                // 拒绝策略，满队列后，舍弃队列最旧线程
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
            default:
                // 拒绝策略，满队列后，拒绝并抛出异常
                executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
                break;
        }
        executor.initialize();
        return executor;
    }
}
