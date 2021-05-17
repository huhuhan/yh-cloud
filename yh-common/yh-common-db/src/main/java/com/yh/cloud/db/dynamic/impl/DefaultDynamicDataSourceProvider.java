package com.yh.cloud.db.dynamic.impl;

import com.yh.cloud.db.dynamic.AbstractDataSourceProvider;
import com.yh.cloud.db.properties.DataSourceProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * 数据库源，默认加载方式
 *
 * @author yanghan
 * @date 2021/2/4
 */
@AllArgsConstructor
@Data
public class DefaultDynamicDataSourceProvider extends AbstractDataSourceProvider {
    private final Map<String, DataSourceProperty> dataSourcePropertiesMap;

    @Override
    public Map<Object, Object> loadDataSources() {
        return super.createDataSourceMap(this.dataSourcePropertiesMap);
    }

}
