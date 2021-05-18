package com.yh.common.web.service.impl;

import com.yh.common.web.model.ICurrentUser;
import com.yh.common.web.service.ICurrentUserService;

/**
 * 提供一个默认实现类。实际业务自定义实现类。
 * @author yanghan
 * @date 2021/3/26
 */
public class DefaultCurrentUserServiceImpl implements ICurrentUserService {
    @Override
    public ICurrentUser getCurrentUserByUserId(String userId) {
        return null;
    }

    @Override
    public ICurrentUser getCurrentUserByUserName(String username) {
        return null;
    }
}
