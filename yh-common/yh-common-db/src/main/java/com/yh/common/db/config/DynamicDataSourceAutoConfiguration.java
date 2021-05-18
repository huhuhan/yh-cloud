package com.yh.common.db.config;

import com.yh.common.db.dynamic.MyDynamicDataSourceProvider;
import com.yh.common.db.dynamic.MyDynamicRoutingDataSource;
import com.yh.common.db.dynamic.impl.DefaultDynamicDataSourceProvider;
import com.yh.common.db.properties.DataSourceProperty;
import com.yh.common.db.properties.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/2/4
 */
@Configuration
@EnableConfigurationProperties({DynamicDataSourceProperties.class})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@ConditionalOnProperty(
        prefix = "spring.datasource.dynamic-yh",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = false
)
@Slf4j
public class DynamicDataSourceAutoConfiguration {

    @Resource
    private DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public MyDynamicDataSourceProvider dynamicDataSourceProvider() {
        Map<String, DataSourceProperty> datasourceMap = this.properties.getDatasource();
        return new DefaultDynamicDataSourceProvider(datasourceMap);
    }


    /**
     * 注入Bean的ID，取dataSource，则不可与其他数据库共用，需要排除
     * 注入Bean的ID，非dataSource，可用与其他数据库员共存，需要单独注入sqlTemplate
     * @param dynamicDataSourceProvider
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource dynamicRoutingDataSource(MyDynamicDataSourceProvider dynamicDataSourceProvider) {
        MyDynamicRoutingDataSource dynamicRoutingDataSource = new MyDynamicRoutingDataSource();
        dynamicRoutingDataSource.setMyDynamicDataSourceProvider(dynamicDataSourceProvider);
        dynamicRoutingDataSource.setPrimary(this.properties.getPrimary());
        return dynamicRoutingDataSource;
    }

}
