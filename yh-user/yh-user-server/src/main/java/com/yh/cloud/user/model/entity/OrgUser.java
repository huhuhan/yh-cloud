package com.yh.cloud.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yh.common.db.model.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 用户表
 *
 * @author yanghan
 * @date 2021-09-13 17:43:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("yh_org_user")
@ApiModel(value = "OrgUser", description = "用户表")
public class OrgUser extends SuperEntity {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "登录名（账户）不能为空")
    @ApiModelProperty(value = "登录名（账户）")
    @TableField(value = "username_")
    private String username;
    @NotBlank(message = "名称（昵称）不能为空")
    @ApiModelProperty(value = "名称（昵称）")
    @TableField(value = "nickname_")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    @TableField(value = "password_")
    private String password;
    @ApiModelProperty(value = "邮箱")
    @TableField(value = "email_")
    private String email;
    @ApiModelProperty(value = "手机号码")
    @TableField(value = "mobile_")
    private String mobile;
    @ApiModelProperty(value = "地址")
    @TableField(value = "address_")
    private String address;
    @ApiModelProperty(value = "照片（头像）")
    @TableField(value = "photo_")
    private String photo;
    @ApiModelProperty(value = "性别：男，女，未知")
    @TableField(value = "sex_")
    private String sex;
    @ApiModelProperty(value = "来源")
    @TableField(value = "from_")
    private String from;
    @ApiModelProperty(value = "状态，数据字典，系统用户状态")
    @TableField(value = "status_")
    private String status;
    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by_")
    private String createBy;
    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by_")
    private String updateBy;
    @ApiModelProperty(value = "是否删除，0否1是")
    @TableField(value = "delete_flag_")
    private Integer deleteFlag;
}
