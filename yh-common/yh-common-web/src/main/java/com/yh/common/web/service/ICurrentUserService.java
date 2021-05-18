package com.yh.common.web.service;

import com.yh.common.web.model.ICurrentUser;

/**
 * 系统用户对象操作接口
 * @author yanghan
 * @date 2020/11/27
 */
public interface ICurrentUserService {
    /**
     * 根据用户唯一ID获取对象
     * @param userId 唯一ID
     * @return ICurrentUser
     */
    ICurrentUser getCurrentUserByUserId(String userId);

    /**
     * 根据用户唯一登录名获取对象
     * @param username 唯一用户名称
     * @return ICurrentUser
     */
    ICurrentUser getCurrentUserByUserName(String username);
}
