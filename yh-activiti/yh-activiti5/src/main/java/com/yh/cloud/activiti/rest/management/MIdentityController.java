package com.yh.cloud.activiti.rest.management;

import com.yh.cloud.activiti.service.management.MIdentityService;
import com.yh.cloud.web.wrapper.ReturnWrapMapper;
import com.yh.cloud.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Activiti用户、组管理
 *
 * @author yanghan
 * @date 2019/10/31
 */
@Api(tags = "Activiti用户、组管理")
@RestController
@RequestMapping("/management/identity")
@Slf4j
public class MIdentityController {

    @ApiOperation("初始化Demo数据")
    @GetMapping("init-demo")
    public void initDemo() {
        List<String> INNER_GROUPS = Arrays.asList("adminDemo", "userDemo", "hrDemo", "deptLeaderDemo");
        List<String> INNER_USERS = Arrays.asList("adminDemo", "kafeituDemo", "hruserDemo", "leaderuserDemo");

        INNER_GROUPS.forEach(group -> {
            mIdentityService.deleteGroup(group);
            String type = group.indexOf("user") != -1 || group.indexOf("admin") != -1 ? "security-role" : "assignment";
            mIdentityService.saveGroup(group, group, type);

        });

        INNER_USERS.forEach(user -> {
            mIdentityService.deleteUser(user);
            mIdentityService.saveUser(user, user, user, "000000", "");
        });
    }

    @Autowired
    private MIdentityService mIdentityService;

    @ApiOperation("组列表")
    @GetMapping("group/list")
    public ReturnWrapper groupList(@RequestParam(defaultValue = "1000") Integer pageSize,
                                   @RequestParam(defaultValue = "1") Integer pageNum) {
        return ReturnWrapMapper.ok(mIdentityService.getGroupList(pageSize, pageNum));
    }

    @ApiOperation("保存组")
    @PostMapping(value = "group/save")
    public ReturnWrapper saveGroup(@RequestParam("groupId") String groupId,
                                   @RequestParam("groupName") String groupName,
                                   @RequestParam("type") String type) {
        mIdentityService.saveGroup(groupId, groupName, type);
        log.info("成功添加组[" + groupName + "]");
        return ReturnWrapMapper.ok();
    }

    @ApiOperation("删除组")
    @PostMapping(value = "group/delete")
    public ReturnWrapper deleteGroup(@RequestParam("groupId") String groupId) {
        mIdentityService.deleteGroup(groupId);
        log.info("成功删除组[" + groupId + "]");
        return ReturnWrapMapper.ok();
    }


    @ApiOperation("用户列表")
    @GetMapping("user/list")
    public ReturnWrapper userList(@RequestParam(defaultValue = "1000") Integer pageSize,
                                  @RequestParam(defaultValue = "1") Integer pageNum) {
        return ReturnWrapMapper.ok(mIdentityService.getUserList(pageSize, pageNum));
    }

    @ApiOperation("保存用户")
    @PostMapping(value = "user/save")
    public ReturnWrapper saveUser(@RequestParam("userId") String userId,
                                  @RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam(value = "password", required = false) String password,
                                  @RequestParam(value = "email", required = false) String email) {
        mIdentityService.saveUser(userId, firstName, lastName, password, email);
        log.info("成功添加用户[" + firstName + " " + lastName + "]");
        return ReturnWrapMapper.ok();
    }

    @ApiOperation("删除用户")
    @PostMapping(value = "user/delete")
    public ReturnWrapper deleteUser(@RequestParam("userId") String userId) {
        mIdentityService.deleteUser(userId);
        log.info("成功删除用户[" + userId + "]");
        return ReturnWrapMapper.ok();
    }

    @ApiOperation("为用户设置所属组")
    @PostMapping(value = "user/set-group")
    public ReturnWrapper groupForUser(@RequestParam("userId") String userId,
                                      @RequestParam("group") String[] groupIds) {
        mIdentityService.groupForUser(userId, groupIds);
        return ReturnWrapMapper.ok();
    }

}
