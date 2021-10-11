package com.yh.cloud.generator.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据表信息，参考information_schema.tables
 *
 * @author yanghan
 * @date 2021/6/3
 */
@Data
public class TablePo {
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("表注释")
    private String tableComment;
    @ApiModelProperty("创建时间")
    private String createTime;
}
