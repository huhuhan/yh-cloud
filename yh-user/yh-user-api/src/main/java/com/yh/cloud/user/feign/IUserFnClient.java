package com.yh.cloud.user.feign;

import com.yh.cloud.user.feign.fallback.UserFnClientFallbackFactory;
import com.yh.common.web.model.entity.CurrentUser;
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

    @PostMapping("/f/orguser/id")
    CurrentUser queryById(@RequestParam(value = "userId") String userId);

    @PostMapping("/f/orguser/username")
    CurrentUser queryByUsername(@RequestParam(value = "username") String username);
}
