package com.yh.cloud.user.controller.feign;

import com.yh.cloud.user.service.IOrgUserService;
import com.yh.common.web.model.entity.CurrentUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 区分feign调用的接口，便于后续统一拦截或其他处理
 *
 * @author yanghan
 * @date 2021-09-13 17:43:00
 */
@RestController
@RequestMapping("/f/orguser")
@ApiIgnore
public class FOrgUserController {
    @Autowired
    private IOrgUserService orgUserService;

    @ApiOperation(value = "根据登录名查询用户")
    @PostMapping("/username")
    public CurrentUser queryByUsername(@RequestParam String username) {
        return orgUserService.queryByUsername(username);
    }

    @ApiOperation(value = "根据用户ID查询用户")
    @PostMapping("/id")
    public CurrentUser queryByUserId(@RequestParam String userId) {
        return orgUserService.queryByUserId(userId);
    }
}
