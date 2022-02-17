package com.yh.common.log.model;

import java.io.Serializable;

/**
 * 当前用户信息对象，保证基本字段
 *
 * @author yanghan
 * @date 2020/6/2
 */
public interface ICurrentUser extends Serializable {
    /**
     * 唯一ID
     *
     * @return java.land.String
     */
    String getUserId();

    /**
     * 唯一用户名称，登录账户
     *
     * @return java.land.String
     */
    String getUsername();

    /**
     * 密码
     *
     * @return java.land.String
     */
    String getPassword();

    /**
     * 用户昵称
     *
     * @return java.land.String
     */
    String getNickname();


    /**
     * 是否有效
     *
     * @return boolean
     */
    boolean isEnabled();

    /**
     * 唯一租户ID
     *
     * @return java.land.String
     */
    String getTenantId();
}
