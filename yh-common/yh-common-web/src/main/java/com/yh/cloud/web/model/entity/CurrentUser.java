package com.yh.cloud.web.model.entity;

import com.yh.cloud.web.model.ICurrentUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统当前登录用户对象，用户表可继承该类
 *
 * @author yanghan
 * @date 2019/6/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser implements ICurrentUser {

    private String userId;
    private String username;
    private String nickname;
    private boolean enabled;
    private String password;
    /** 租户ID */
    private String tenantId;


}
