package com.yh.cloud.demo.rest;

import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author yanghan
 * @date 2019/7/2
 */
@RestController
@RequestMapping("/auth")
public class HelloController {

    @GetMapping("/user")
    public Authentication user(Authentication authentication) {
        return authentication;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ReturnWrapper<String> testNoLogin() {
        return ReturnWrapMapper.ok("Hello!");
    }

    @RequestMapping(value = "/test-yh", method = RequestMethod.POST)
    public ReturnWrapper<String> testAccess() {
        return ReturnWrapMapper.ok("Hello! yh");
    }
}
