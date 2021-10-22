package com.yh.cloud.file.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.yh.common.db.model.entity.SuperEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 系统附件表
 *
 * @author yanghan
 * @date 2021-10-20 11:15:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yh_sys_file")
@ApiModel(value = "SysFile", description = "系统附件表")
public class SysFile extends SuperEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "文件名")
    @TableField(value = "name_")
    private String name;
    @ApiModelProperty(value = "扩展名")
    @TableField(value = "ext_")
    private String ext;
    @ApiModelProperty(value = "上传器，数据字典值")
    @TableField(value = "uploader_")
    private String uploader;
    @ApiModelProperty(value = "路径")
    @TableField(value = "path_")
    private String path;
    @ApiModelProperty(value = "哈希值")
    @TableField(value = "hash_")
    private String hash;
    @ApiModelProperty(value = "逻辑标识")
    @TableField(value = "delete_flag_")
    private Integer deleteFlag;
}
