package com.yh.cloud.generator.model.vo;

import com.yh.common.db.model.query.AbstractPageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yanghan
 * @date 2021/6/2
 */
@Data
public class MySQLQuery<T> extends AbstractPageQuery<T> {
    @ApiModelProperty("表名")
    private String tableName;
}
