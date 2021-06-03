package com.yh.cloud.generator.model.emun;

import lombok.AllArgsConstructor;

/**
 * 数据类型转化
 *
 * @author yanghan
 * @date 2021/6/3
 */
@AllArgsConstructor
public enum DataType {

    MYSQL_TINYINT("mysql", "tinyint", "Integer"),
    MYSQL_SMALLINT("mysql", "smallint", "Integer"),
    MYSQL_MEDIUMINT("mysql", "mediumint", "Integer"),
    MYSQL_INT("mysql", "int", "Integer"),
    MYSQL_INTEGER("mysql", "integer", "Integer"),
    MYSQL_BIGINT("mysql", "bigint", "Long"),
    MYSQL_FLOAT("mysql", "float", "Float"),
    MYSQL_DOUBLE("mysql", "double", "Double"),
    MYSQL_DECIMAL("mysql", "decimal", "BigDecimal"),
    MYSQL_BIT("mysql", "bit", "Boolean"),
    MYSQL_CHAR("mysql", "char", "String"),
    MYSQL_VARCHAR("mysql", "varchar", "String"),
    MYSQL_TINYTEXT("mysql", "tinytext", "String"),
    MYSQL_TEXT("mysql", "text", "String"),
    MYSQL_MEDIUMTEXT("mysql", "mediumtext", "String"),
    MYSQL_LONGTEXT("mysql", "longtext", "String"),
    MYSQL_DATE("mysql", "date", "Date"),
    MYSQL_DATETIME("mysql", "datetime", "Date"),
    MYSQL_TIMESTAMP("mysql", "timestamp", "Date"),

    TEMP("yh", "yh", "YH");

    private String db;

    private String dbType;

    private String javaType;

    public String getDb() {
        return db;
    }

    public String getDbType() {
        return dbType;
    }

    public String getJavaType() {
        return javaType;
    }

    public static String getJavaType(String dataType) {
        for (DataType value : DataType.values()) {
            if (value.getDbType().equals(dataType)) {
                return value.getJavaType();
            }
        }
        return "unKnowType";
    }
}
