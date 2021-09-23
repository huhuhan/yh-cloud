package com.yh.cloud.user.feign.fallback;

import com.yh.cloud.user.feign.IUserFnClient;
import com.yh.common.web.model.entity.CurrentUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 熔断处理
 * @author yanghan
 * @date 2020/12/2
 */
@Slf4j
@Component
public class UserFnClientFallbackFactory implements FallbackFactory<IUserFnClient> {
    @Override
    public IUserFnClient create(Throwable throwable) {
        return new IUserFnClient() {
            @Override
            public CurrentUser queryById(String userId) {
                log.error("Feign调用异常| {} | {}", throwable.getMessage(), userId);
                return new CurrentUser();
            }

            @Override
            public CurrentUser queryByUsername(String username) {
                log.error("Feign调用异常| {} | {}", throwable.getMessage(), username);
                return new CurrentUser();
            }
        };
    }
}
