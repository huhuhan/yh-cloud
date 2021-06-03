package com.yh.cloud.generator.model.po;

import lombok.Data;

/**
 * 数据表字段信息，参考information_schema.columns
 * @author yanghan
 * @date 2021/6/3
 */
@Data
public class MySqlColumnPo {

    /** 字段名 */
    private String columnName;
    /** 类型 */
    private String dataType;
    /** 注释 */
    private String columnComment;
    /** 键 */
    private String columnKey;
    /**  */
    private String extra;
}
