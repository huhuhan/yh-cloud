package com.yh.common.web.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yanghan
 * @date 2019/7/30
 */
@Data
public class SysUser {

    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("账号")
    private String loginName;
    @ApiModelProperty("角色")
    private List<SysRole> roles;
}
