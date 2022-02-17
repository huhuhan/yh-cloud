package com.yh.common.log.service;

import com.yh.common.log.model.ICurrentUser;

/**
 * @author yanghan
 * @date 2022/2/17
 */
public interface CurrentUserLogService {

    /**
     * 获取当前登录用户
     *
     * @return ICurrentUser
     */
    ICurrentUser getCurrentUser();
}
