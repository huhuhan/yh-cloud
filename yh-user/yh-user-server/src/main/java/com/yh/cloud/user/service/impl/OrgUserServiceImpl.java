package com.yh.cloud.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.cloud.base.constant.BaseConstant;
import com.yh.cloud.user.mapper.OrgUserMapper;
import com.yh.cloud.user.model.entity.OrgUser;
import com.yh.cloud.user.model.vo.OrgUserQuery;
import com.yh.cloud.user.model.vo.UpdateOrgUserVO;
import com.yh.cloud.user.service.IOrgUserService;
import com.yh.common.db.service.impl.SuperServiceImpl;
import com.yh.common.web.exception.BusinessException;
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
        this.userIsUnique(true, orgUser.getUsername(), orgUser.getMobile(), orgUser.getEmail());

        orgUser.setPassword(passwordEncoder.encode(BaseConstant.DEFAULT_PWD));
        // todo: 数据字典工具类
        orgUser.setStatus("01");
        this.saveOrUpdate(orgUser);

        return orgUser;
    }

    @Override
    public OrgUser updateById(UpdateOrgUserVO userVO) {
        OrgUser orgUser = this.getById(userVO.getId());
        if (orgUser != null) {
            if (StrUtil.isNotBlank(userVO.getMobile())) {
                this.mobileIsUnique(true, userVO.getMobile());
            }
            if (StrUtil.isNotBlank(userVO.getEmail())) {
                this.emailIsUnique(true, userVO.getEmail());
            }
            BeanUtil.copyProperties(userVO, orgUser);
            this.updateById(orgUser);
            return this.getById(orgUser.getId());
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        OrgUser orgUser = this.getById(id);
        if (null != orgUser) {
            orgUser.setDeleteFlag(BooleanUtil.toInt(Boolean.TRUE));
            return this.updateById(orgUser);
        }
        return false;
    }

    @Override
    public boolean userIsUnique(boolean throwTrueError, String username, String mobile, String email) {
        int usernameCount = this.count(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getUsername, username));
        boolean mobileIsUnique = this.mobileIsUnique(false, mobile);
        boolean emailIsUnique = this.emailIsUnique(false, email);
        if (usernameCount > 0 || mobileIsUnique || emailIsUnique) {
            if (throwTrueError) {
                throw new BusinessException(ReturnCode.USER_01);
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean mobileIsUnique(boolean throwTrueError, String mobile) {
        boolean flag = this.count(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getMobile, mobile)) > 0;
        if (flag && throwTrueError) {
            throw new BusinessException(ReturnCode.USER_04);
        }
        return flag;
    }

    private boolean emailIsUnique(boolean throwTrueError, String email) {
        boolean flag = this.count(Wrappers.<OrgUser>lambdaQuery().eq(OrgUser::getEmail, email)) > 0;
        if (flag && throwTrueError) {
            throw new BusinessException(ReturnCode.USER_05);
        }
        return flag;
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
