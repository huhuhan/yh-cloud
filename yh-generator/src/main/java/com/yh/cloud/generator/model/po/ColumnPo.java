package com.yh.cloud.generator.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据表字段信息，参考information_schema.columns
 *
 * @author yanghan
 * @date 2021/6/3
 */
@Data
public class ColumnPo {

    @ApiModelProperty("字段名")
    private String columnName;
    @ApiModelProperty("类型")
    private String dataType;
    @ApiModelProperty("注释")
    private String columnComment;
    @ApiModelProperty("键")
    private String columnKey;
    @ApiModelProperty("")
    private String extra;
}
