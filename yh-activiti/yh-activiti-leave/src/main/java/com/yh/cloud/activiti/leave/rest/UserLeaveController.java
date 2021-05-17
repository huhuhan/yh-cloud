package com.yh.cloud.activiti.leave.rest;


import com.yh.cloud.activiti.leave.model.domain.UserLeave;
import com.yh.cloud.activiti.leave.service.IUserLeaveService;
import com.yh.cloud.web.annotation.CUser;
import com.yh.cloud.web.model.entity.CurrentUser;
import com.yh.cloud.web.wrapper.ReturnWrapMapper;
import com.yh.cloud.web.wrapper.ReturnWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * <p>
 * 请假表 前端控制器
 * </p>
 *
 * @author yanghan
 * @since 2019-10-25
 */
@RestController
@RequestMapping("/user-leave")
public class UserLeaveController {

    @Autowired
    private IUserLeaveService iUserLeaveService;


    @PostMapping("/add")
    @ApiOperation(value = "添加", consumes = "application/json")
    public ReturnWrapper addUserLeave(@ApiIgnore @CUser CurrentUser currentUser, @RequestBody UserLeave userLeave) {
        System.out.println("当前用户：" + currentUser.getUsername());
        iUserLeaveService.addUserLeave(currentUser, userLeave);
        return ReturnWrapMapper.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", consumes = "application/json")
    public ReturnWrapper updateUserLeave(@ApiIgnore @CUser CurrentUser currentUser, UserLeave userLeave) {
        System.out.println("当前用户：" + currentUser.getUsername());
        iUserLeaveService.saveOrUpdate(userLeave);
        return ReturnWrapMapper.ok();
    }

    @GetMapping("/list")
    @ApiOperation(value = "列表")
    public ReturnWrapper userLeaveList(@ApiIgnore @CUser CurrentUser currentUser) {
        System.out.println("当前用户：" + currentUser.getUsername());
        return ReturnWrapMapper.ok(iUserLeaveService.userLeaveList(currentUser));
    }

    @PostMapping("/complete")
    @ApiOperation(value = "办理", consumes = "application/json")
    public ReturnWrapper completeLeaveTask(@ApiIgnore @CUser CurrentUser currentUser, @RequestBody Map<String, Object> param) {
        System.out.println("当前用户：" + currentUser.getUsername());
        iUserLeaveService.completeLeaveTask(currentUser, param);
        return ReturnWrapMapper.ok();
    }
}

