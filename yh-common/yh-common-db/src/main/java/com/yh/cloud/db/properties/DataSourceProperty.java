package com.yh.cloud.db.properties;

import lombok.Data;

import javax.sql.DataSource;

/**
 * 数据库源配置属性对象
 * @author yanghan
 * @date 2021/2/4
 */
@Data
public class DataSourceProperty {
//    /** 默认数据库连接池 */
//    private final String DEFAULT_SOURCE_TYPE = "com.alibaba.druid.pool.DruidDataSource";
    /** 数据库源连接池类型 */
    private Class<? extends DataSource> sourceType;
    /** 数据库驱动 */
    private String driverClassName;
    /** 连接地址 */
    private String url;
    /** 账号 */
    private String username;
    /** 密码 */
    private String password;
}
