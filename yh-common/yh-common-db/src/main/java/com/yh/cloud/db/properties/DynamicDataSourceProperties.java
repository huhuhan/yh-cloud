package com.yh.cloud.db.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 动态数据库源配置属性加载
 * @author yanghan
 * @date 2021/2/4
 */
@ConfigurationProperties(
        prefix = "spring.datasource.dynamic-yh"
)
@Data
public class DynamicDataSourceProperties {
    /** 默认数据库源 */
    private String primary = "master";
    /** 数据库配置集合 */
    private Map<String, DataSourceProperty> datasource;
}
