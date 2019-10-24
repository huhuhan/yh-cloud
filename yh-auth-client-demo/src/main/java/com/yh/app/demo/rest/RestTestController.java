package com.yh.app.demo.rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanghan
 * @date 2019/7/2
 */
@RestController
public class RestTestController {

    @GetMapping("/")
    public String home() {
        return "hello world";
    }

    @GetMapping("/user")
    public Authentication user(Authentication authentication) {
        return authentication;
    }

}
