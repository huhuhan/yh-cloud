package com.yh.cloud.auth.rest;

import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
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

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ReturnWrapper<String> testNoLogin() {
        return ReturnWrapMapper.ok("Hello!");
    }

    @RequestMapping(value = "/test-admin", method = RequestMethod.POST)
    public ReturnWrapper<String> testLogin() {
        return ReturnWrapMapper.ok("Hello! admin");
    }

    @RequestMapping(value = "/test-yh", method = RequestMethod.POST)
    public ReturnWrapper<String> testAccess() {
        return ReturnWrapMapper.ok("Hello! yh");
    }
}
