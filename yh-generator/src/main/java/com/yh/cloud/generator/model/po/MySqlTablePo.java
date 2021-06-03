package com.yh.cloud.generator.model.po;

import lombok.Data;

/**
 * 数据表信息，参考information_schema.tables
 * @author yanghan
 * @date 2021/6/3
 */
@Data
public class MySqlTablePo {
    /** 表名  */
    private String tableName;
    /** 注释 */
    private String tableComment;
    /** 创建时间 */
    private String createTime;
}
