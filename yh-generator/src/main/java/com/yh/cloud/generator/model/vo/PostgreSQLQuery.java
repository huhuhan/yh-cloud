package com.yh.cloud.generator.model.vo;

import com.yh.common.db.model.query.AbstractPageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostgreSQLQuery<T> extends AbstractPageQuery<T> {
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("模式，默认public")
    private String schema = "public";
}
