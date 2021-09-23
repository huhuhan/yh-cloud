package com.yh.cloud.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.user.model.entity.OrgUser;
import com.yh.cloud.user.model.vo.OrgUserQuery;
import com.yh.common.db.service.ISuperService;
import com.yh.common.web.model.entity.CurrentUser;

/**
 * 用户表
 *
 * @author yanghan
 * @date 2021-09-13 17:43:00
 */
public interface IOrgUserService extends ISuperService<OrgUser> {
    /**
     * 列表
     *
     * @param orgUserQuery
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author yanghan
     * @date 2021-09-13 17:43:00
     */
    IPage<OrgUser> findList(OrgUserQuery<OrgUser> orgUserQuery);

    /**
     * 用户是否存在
     *
     * @author yanghan
     * @date 2021/9/16
     */
    boolean isUserExist(OrgUser orgUser);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return boolean
     * @author yanghan
     * @date 2021/9/16
     */
    boolean changePwd(String userId, String oldPassword, String newPassword);

    /**
     * 根据用户登录名获取当前登录对象
     *
     * @param username
     * @return com.yh.common.web.model.entity.CurrentUser
     * @author yanghan
     * @date 2021/9/17
     */
    CurrentUser queryByUsername(String username);

    /**
     * 根据用户唯一ID获取当前登录对象
     *
     * @param userId
     * @return com.yh.common.web.model.entity.CurrentUser
     * @author yanghan
     * @date 2021/9/17
     */
    CurrentUser queryByUserId(String userId);

    /**
     * 保存用户
     *
     * @param orgUser
     * @return com.yh.cloud.user.model.entity.OrgUser
     * @author yanghan
     * @date 2021/9/17
     */
    OrgUser saveUser(OrgUser orgUser);
}

