package com.yh.demo.api.bpm.fallback;

import com.yh.common.web.model.entity.CurrentUser;
import com.yh.demo.api.bpm.IUserFnClient;
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
            public CurrentUser getUserById(String userId) {
                log.error("Feign调用异常| {} | {}", throwable.getMessage(), userId);
                return new CurrentUser();
            }

            @Override
            public CurrentUser getUserByUsername(String username) {
                log.error("Feign调用异常| {} | {}", throwable.getMessage(), username);
                return new CurrentUser();
            }
        };
    }
}
