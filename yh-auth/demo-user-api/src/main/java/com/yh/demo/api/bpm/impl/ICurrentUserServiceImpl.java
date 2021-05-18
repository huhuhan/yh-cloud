package com.yh.demo.api.bpm.impl;

import com.yh.common.web.model.entity.CurrentUser;
import com.yh.common.web.service.ICurrentUserService;
import com.yh.demo.api.bpm.IUserFnClient;
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
    public CurrentUser getCurrentUserByUserId(String userId) {
        return iUserFnClient.getUserById(userId);
    }

    @Override
    public CurrentUser getCurrentUserByUserName(String username) {
        return iUserFnClient.getUserByUsername(username);
    }
}
