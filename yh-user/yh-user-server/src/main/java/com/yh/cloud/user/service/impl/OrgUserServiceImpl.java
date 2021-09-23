package com.yh.cloud.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.cloud.base.constant.BaseConstant;
import com.yh.cloud.user.mapper.OrgUserMapper;
import com.yh.cloud.user.model.entity.OrgUser;
import com.yh.cloud.user.model.vo.OrgUserQuery;
import com.yh.cloud.user.service.IOrgUserService;
import com.yh.common.db.service.impl.SuperServiceImpl;
import com.yh.common.web.exception.BusinessException;
import com.yh.common.web.exception.ParameterException;
import com.yh.common.web.model.entity.CurrentUser;
import com.yh.common.web.wrapper.ReturnCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户表
 *
 * @author yanghan
 * @date 2021-09-13 17:43:00
 */
@Service
public class OrgUserServiceImpl extends SuperServiceImpl<OrgUserMapper, OrgUser> implements IOrgUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<OrgUser> findList(OrgUserQuery<OrgUser> orgUserQuery) {
        IPage<OrgUser> pageQuery = orgUserQuery.initPageQuery();
        QueryWrapper<OrgUser> queryWrapper = Wrappers.query();
        orgUserQuery.initFilter(queryWrapper);
        return this.page(pageQuery, queryWrapper);
    }


    @Override
    public CurrentUser queryByUsername(String username) {
        OrgUser user = this.getOne(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getUsername, username));
        return this.toCurrentUser(user);
    }

    @Override
    public CurrentUser queryByUserId(String userId) {
        OrgUser user = this.getById(userId);
        return this.toCurrentUser(user);
    }

    private CurrentUser toCurrentUser(OrgUser orgUser) {
        if (null == orgUser) {
            return null;
        }
        CurrentUser currentUser = new CurrentUser();
        currentUser.setUserId(orgUser.getId().toString());
        currentUser.setEnabled(orgUser.getStatus().equals("01"));
        BeanUtils.copyProperties(orgUser, currentUser);

        // 补充其他字段
        return currentUser;
    }

    @Override
    public OrgUser saveUser(OrgUser orgUser) {
        this.isUserExist(orgUser);

        orgUser.setPassword(passwordEncoder.encode(BaseConstant.DEFAULT_PWD));
        // todo: 数据字典工具类
        orgUser.setStatus("01");
        this.saveOrUpdate(orgUser);

        return orgUser;
    }

    @Override
    public boolean isUserExist(OrgUser orgUser) {
        if (null == orgUser) {
            throw new ParameterException("orgUser");
        }
        // 验证唯一字段
        int usernameCount = this.count(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getUsername, orgUser.getUsername()));
        int mobileCount = this.count(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getMobile, orgUser.getMobile()));
        int emailCount = this.count(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getEmail, orgUser.getEmail()));
        if (usernameCount > 0 || mobileCount > 0 || emailCount > 0) {
            throw new BusinessException(ReturnCode.USER_01);
        }
        return false;
    }

    @Override
    public boolean changePwd(String userId, String oldPassword, String newPassword) {
        OrgUser orgUser = this.getById(userId);
        if (null == orgUser) {
            throw new BusinessException(ReturnCode.USER_02);
        }

        if (StrUtil.isNotBlank(oldPassword) && !passwordEncoder.matches(oldPassword, orgUser.getPassword())) {
            throw new BusinessException(ReturnCode.USER_03);
        }

        orgUser.setPassword(passwordEncoder.encode(newPassword));
        return this.updateById(orgUser);
    }

}
