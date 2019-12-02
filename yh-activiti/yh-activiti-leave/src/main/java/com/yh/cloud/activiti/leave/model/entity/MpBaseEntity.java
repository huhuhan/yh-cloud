package com.yh.cloud.activiti.leave.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体公共父类
 *
 * @author yanghan
 * @date 2019/10/28
 */
@Data
public class MpBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建用户ID")
    private Long createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新用户ID")
    private Long updateBy;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleteFlag;
}
