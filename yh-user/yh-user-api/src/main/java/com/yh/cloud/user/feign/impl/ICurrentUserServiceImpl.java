package com.yh.cloud.user.feign.impl;

import com.yh.cloud.user.feign.IUserFnClient;
import com.yh.common.web.model.entity.CurrentUser;
import com.yh.common.web.service.ICurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yanghan
 * @date 2020/12/2
 */
@Component
public class ICurrentUserServiceImpl implements ICurrentUserService {
    @Autowired
    private IUserFnClient iUserFnClient;

    @Override
    public CurrentUser getByUserId(String userId) {
        return iUserFnClient.queryById(userId);
    }

    @Override
    public CurrentUser getByUserName(String username) {
        return iUserFnClient.queryByUsername(username);
    }
}
