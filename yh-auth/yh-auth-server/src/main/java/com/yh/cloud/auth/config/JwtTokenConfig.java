package com.yh.cloud.auth.config;

import com.yh.common.auth.token.TokenEnhancerInfoService;
import com.yh.common.web.model.ICurrentUser;
import com.yh.common.web.service.ICurrentUserService;
import com.yh.cloud.auth.authentication.userdetails.MyUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken的配置
 *
 * @author yanghan
 * @date 2019/7/9
 */
@Configuration
public class JwtTokenConfig {

    @Resource
    private ICurrentUserService iCurrentUserService;

    @Bean
    public TokenEnhancerInfoService tokenEnhancerInfoService() {
        /** 令牌增强器，扩展信息 */
        return oAuth2Authentication -> {
            Map<String, Object> info = new HashMap<>(4);

            Object principal = oAuth2Authentication.getPrincipal();
            if (principal instanceof MyUserDetails) {
                MyUserDetails myUserDetails = (MyUserDetails) oAuth2Authentication.getPrincipal();


                if (null != myUserDetails) {
                    info.put("userId", myUserDetails.getUserId());
                    info.put("username", myUserDetails.getUsername());
                    info.put("nickname", myUserDetails.getNickname());
                    // 租户唯一ID
                    ICurrentUser currentUser = iCurrentUserService.getByUserId(myUserDetails.getUserId());
                    info.put("tenantId", currentUser.getTenantId());
                }
            } else {
                // 授权类型：client_credentials的默认实现类，不支持刷新token，即没有refreshToken
                info.put("principal", principal.toString());
            }

            return info;
        };
    }
}
