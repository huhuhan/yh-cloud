package com.yh.cloud.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.user.model.entity.OrgUser;
import com.yh.cloud.user.model.vo.OrgUserQuery;
import com.yh.cloud.user.model.vo.UpdateOrgUserVO;
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
     * 用户是否唯一
     *
     * @param throwTrueError true则抛出异常
     * @param username       登录名（账户）
     * @param mobile         手机号
     * @param email          邮箱
     * @return boolean
     * @author yanghan
     * @date 2021/10/12
     */
    boolean userIsUnique(boolean throwTrueError, String username, String mobile, String email);

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

    /**
     * 删除用户，逻辑删除
     *
     * @param id
     * @return boolean
     * @author yanghan
     * @date 2021/9/17
     */
    boolean deleteById(Long id);

    /**
     * 更新基本信息
     *
     * @param userVO
     * @return com.yh.cloud.user.model.entity.OrgUser
     * @author yanghan
     * @date 2021/9/17
     */
    OrgUser updateById(UpdateOrgUserVO userVO);
}

