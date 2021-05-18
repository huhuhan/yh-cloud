package com.yh.common.db.dynamic;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 动态数据库源实现类
 * 采用Spring扩展的AbstractRoutingDataSource
 * 或自己扩展集成org.springframework.jdbc.datasource.AbstractDataSource
 *MyDynamicDataSourceProvider
 * @author yanghan
 * @date 2021/2/3
 */
@Setter
@Slf4j
public class MyDynamicRoutingDataSource extends AbstractRoutingDataSource {

    private MyDynamicDataSourceProvider myDynamicDataSourceProvider;
    private String primary;

    @Override
    protected Object determineCurrentLookupKey() {
        return MyDynamicDataSourceContextHolder.getDataSourceType();
    }

    @Override
    public void afterPropertiesSet() {
        Map<Object, Object> dataSourceMap;
        Object defaultDataSource;
        try {
            dataSourceMap = myDynamicDataSourceProvider.loadDataSources();
            defaultDataSource = dataSourceMap.get(this.primary);
        } catch (Exception e) {
            throw new RuntimeException("找不到数据库源，无法加载！");
        }

        // 初始化加载
        super.setTargetDataSources(dataSourceMap);
        super.setDefaultTargetDataSource(defaultDataSource);
        super.afterPropertiesSet();
        log.info("数据源加载成功，默认【{}】，包括其他共{}个", this.primary, dataSourceMap.size());
    }

    /**
     * 获取指定数据库源，父类的目标数据库源集合
     *
     * @param key
     * @return boolean
     */
    public DataSource getTargetDataSource(String key) {
        Map<Object, DataSource> targetDataSources = this.toGetTargetDataSources();
        return targetDataSources.get(key);
    }

    /**
     * 返回当前数据库源
     *
     * @return javax.sql.DataSource
     */
    public DataSource getDefaultDataSource() {
        return (DataSource) this.toGetRoutingValue(this, "resolvedDefaultDataSource");
    }


    /**
     * 获取父类的目标数据库源集合
     * Map的value类型为Object，支持String和DataSource，在同步解析都转为DataSource
     * 这里直接全部采取DataSource
     *
     * @return java.util.Map<java.lang.String, javax.sql.DataSource>
     */

    private Map<Object, DataSource> toGetTargetDataSources() {
        return (Map<Object, DataSource>) this.toGetRoutingValue(this, "targetDataSources");
    }

    /**
     * 同步操作，添加到，父类的目标数据库源集合
     *
     * @param key
     * @param dataSource
     */
    public synchronized void addDataSource(String key, DataSource dataSource) {
        Map<Object, DataSource> targetDataSources = this.toGetTargetDataSources();
        DataSource oldDataSource = targetDataSources.get(key);
        if (null != oldDataSource) {
            // todo: 关闭数据库源
        }
        targetDataSources.put(key, dataSource);

        // 重点，目标数据库源解析为真正的数据库源对象
        super.afterPropertiesSet();
    }

    /**
     * 同步操作，删除数据库源
     *
     * @param key
     */
    public synchronized void removeDataSource(String key) {
        Map<Object, DataSource> targetDataSources = this.toGetTargetDataSources();
        if (targetDataSources.containsKey(key)) {
            DataSource oldDataSource = targetDataSources.remove(key);
            if (null != oldDataSource) {
                // todo: 关闭数据库源
            }
            // 重点，目标数据库源解析为真正的数据库源对象
            super.afterPropertiesSet();
        }
    }

    /**
     * 因为Spring扩展的AbstractRoutingDataSource属性没有开放get方法，通过
     *
     * @param instance
     * @param fieldName
     * @return
     */
    private Object toGetRoutingValue(Object instance, String fieldName) {
        try {
            //从AbstractRoutingDataSource获取
            Field field = AbstractRoutingDataSource.class.getDeclaredField(fieldName);
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
