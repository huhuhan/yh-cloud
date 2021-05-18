package com.yh.demo.api.bpm;

import com.yh.common.web.model.entity.CurrentUser;
import com.yh.demo.api.bpm.fallback.UserFnClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户接口
 *
 * @author yanghan
 * @date 2020/12/2
 */
@FeignClient(name = "yh-user", contextId = "userAPI", fallbackFactory = UserFnClientFallbackFactory.class, decode404 = true)
public interface IUserFnClient {

    /**
     * 获取用户对象
     *
     * @param userId
     * @date 2020/12/2
     */
    @PostMapping("/fn-user/get/id")
    CurrentUser getUserById(@RequestParam(value = "userId") String userId);

    @PostMapping("/fn-user/get/username")
    CurrentUser getUserByUsername(@RequestParam(value = "username") String username);
}
