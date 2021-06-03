package com.yh.cloud.generator.model.vo;

import com.yh.common.db.model.query.AbstractPageQuery;

/**
 * @author yanghan
 * @date 2021/6/2
 */
public class GeneratorQuery<T> extends AbstractPageQuery<T> {
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
