package com.yh.cloud.user.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 用户表，可更新的基本信息
 *
 * @author yanghan
 * @date 2021-09-13 17:43:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UpdateOrgUserVO", description = "更新对象")
public class UpdateOrgUserVO {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "名称（昵称）")
    private Long id;
    @ApiModelProperty(value = "名称（昵称）")
    private String nickname;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "照片（头像）")
    private String photo;
    @ApiModelProperty(value = "性别：男，女，未知")
    @TableField(value = "sex_")
    private String sex;
    @ApiModelProperty(value = "状态，数据字典，系统用户状态")
    private String status;
}
