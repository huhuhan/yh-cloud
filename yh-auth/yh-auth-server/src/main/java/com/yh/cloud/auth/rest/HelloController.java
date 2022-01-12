package com.yh.cloud.auth.rest;

import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author yanghan
 * @date 2020/12/8
 */
@RestController
@RequestMapping("/auth")
public class HelloController {

    @ApiOperation("认证即可访问")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ReturnWrapper<String> testNoLogin() {
        return ReturnWrapMapper.ok("Hello!");
    }

    @ApiOperation("认证即可访问")
    @RequestMapping(value = "/test-admin", method = RequestMethod.POST)
    public ReturnWrapper<String> testLogin() {
        return ReturnWrapMapper.ok("Hello! admin");
    }

    @ApiOperation(value = "认证，鉴权要求角色是ROlE_YH")
    @RequestMapping(value = "/test-yh", method = RequestMethod.POST)
    public ReturnWrapper<String> testAccess() {
        return ReturnWrapMapper.ok("Hello! yh");
    }
}
