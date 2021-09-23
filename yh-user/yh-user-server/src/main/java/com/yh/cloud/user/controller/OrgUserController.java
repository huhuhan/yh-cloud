package com.yh.cloud.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.base.constant.BaseConstant;
import com.yh.cloud.user.model.entity.OrgUser;
import com.yh.cloud.user.model.vo.OrgUserQuery;
import com.yh.cloud.user.service.IOrgUserService;
import com.yh.common.web.annotation.CUser;
import com.yh.common.web.exception.BusinessException;
import com.yh.common.web.model.entity.CurrentUser;
import com.yh.common.web.wrapper.ReturnCode;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户表
 *
 * @author yanghan
 * @date 2021-09-13 17:43:00
 */
@RestController
@RequestMapping("/orguser")
@Api(tags = "用户表")
public class OrgUserController {
    @Autowired
    private IOrgUserService orgUserService;

    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public ReturnWrapper<IPage<OrgUser>> findList(OrgUserQuery<OrgUser> orgUserQuery) {
        return ReturnWrapMapper.ok(orgUserService.findList(orgUserQuery));
    }

    @ApiOperation(value = "查询对象")
    @GetMapping("/{id}")
    public ReturnWrapper<OrgUser> findById(@PathVariable Long id) {
        return ReturnWrapMapper.ok(orgUserService.getById(id));
    }

    @ApiOperation(value = "保存or更新")
    @PostMapping("/save")
    public ReturnWrapper save(@RequestBody OrgUser orgUser) {
        return ReturnWrapMapper.ok(orgUserService.saveUser(orgUser));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnWrapper delete(@PathVariable Long id) {
        return ReturnWrapMapper.ok("不支持删除！");
    }

    @ApiOperation(value = "个人修改密码")
    @PostMapping("/pwd/personal-modify")
    public ReturnWrapper modifyPwdByPerson(@ApiIgnore @CUser CurrentUser currentUser,
                                           @RequestParam String oldPassword,
                                           @RequestParam String newPassword) {
        return ReturnWrapMapper.ok(orgUserService.changePwd(currentUser.getUserId(), oldPassword, newPassword));
    }

    @ApiOperation(value = "管理员重置密码")
    @PostMapping("/pwd/admin-modify")
    public ReturnWrapper modifyPwdByAdmin(@ApiIgnore @CUser CurrentUser currentUser,
                                          @RequestParam String password,
                                          @RequestParam String userId) {
        if (currentUser == null || !BaseConstant.ADMIN_USER_ID.equals(currentUser.getUserId())) {
            throw new BusinessException(ReturnCode.UNAUTHORIZED_OPERATION);
        }
        return ReturnWrapMapper.ok(orgUserService.changePwd(userId, null, password));
    }

}
