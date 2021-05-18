package com.yh.cloud.auth.model.domain;

import com.yh.common.web.model.entity.CurrentUser;

/**
 * 模拟数据库用户表实体类
 *
 * @author yanghan
 * @date 2020/12/8
 */
public class SysUser extends CurrentUser {
    public SysUser(String userId, String username, String nickname, boolean enabled, String password, String tenantId) {
        super(userId, username, nickname, enabled, password, tenantId);
    }
}
