package com.yh.cloud.db.dynamic;

/**
 * 动态数据库源切换，上下文对象
 * 存储当前线程中的数据库源
 *
 * @author yanghan
 * @date 2021/2/3
 */
public class MyDynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

}
