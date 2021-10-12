package com.yh.cloud.sys.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.yh.common.db.model.entity.SuperEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 数据字典
 *
 * @author yanghan
 * @date 2021-09-24 16:55:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yh_sys_dict")
@ApiModel(value = "SysDict", description = "数据字典")
public class SysDict extends SuperEntity {
    private static final long serialVersionUID = 1L;
    @NotNull()
    @ApiModelProperty(value = "编码")
    @TableField(value = "code_")
    private String code;
    @NotNull(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    @TableField(value = "name_")
    private String name;
    @NotNull(message = "字典类型不能为空")
    @ApiModelProperty(value = "字典类型")
    @TableField(value = "key_")
    private String key;
    @ApiModelProperty(value = "是否删除，0否1是")
    @TableField(value = "delete_flag_")
    private Integer deleteFlag;
    @ApiModelProperty(value = "分组id")
    @TableField(value = "tree_id_")
    private String treeId;
    @ApiModelProperty(value = "上级id")
    @TableField(value = "parent_id_")
    private String parentId;
    @ApiModelProperty(value = "是否根节点，0否1是")
    @TableField(value = "is_root_")
    private Integer isRoot;
}
