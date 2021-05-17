package com.yh.cloud.db.dynamic;

import cn.hutool.core.collection.CollectionUtil;
import com.yh.cloud.db.properties.DataSourceProperty;
import lombok.Data;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/2/4
 */
@Data
public abstract class AbstractDataSourceProvider implements MyDynamicDataSourceProvider {

    public Map<Object, Object> createDataSourceMap(Map<String, DataSourceProperty> dataSourcePropertyMap) {
        if (CollectionUtil.isEmpty(dataSourcePropertyMap)) {
            return null;
        }
        Map<Object, Object> dataSourceMap = new HashMap<>(dataSourcePropertyMap.size() * 2);

        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertyMap.entrySet()) {
            DataSourceProperty dataSourceProperty = entry.getValue();
            //  采用默认构建器创建
            // todo：后续可以扩展创建接口
            DataSource dataSource = DataSourceBuilder.create()
                    .driverClassName(dataSourceProperty.getDriverClassName())
                    .url(dataSourceProperty.getUrl())
                    .username(dataSourceProperty.getUsername())
                    .password(dataSourceProperty.getPassword())
                    .type(dataSourceProperty.getSourceType())
                    .build();
            dataSourceMap.put(entry.getKey(), dataSource);
        }
        return dataSourceMap;
    }
}
