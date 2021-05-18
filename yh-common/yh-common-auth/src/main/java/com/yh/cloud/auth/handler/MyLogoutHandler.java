package com.yh.cloud.auth.handler;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 扩展：退出登录，后置处理类
 *
 * @author yanghan
 * @date 2021/1/26
 */
@Slf4j
public class MyLogoutHandler implements LogoutHandler {
    @Autowired(required = false)
    private TokenStore tokenStore;

    /**
     * 清除服务端存储的token
     *
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (null == tokenStore) {
            return;
        }
        String token = request.getHeader("Authorization");
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
        }
        if (StrUtil.isNotEmpty(token)) {
            token = token.replace(OAuth2AccessToken.BEARER_TYPE + " ", "");
            OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
            OAuth2RefreshToken refreshToken;
            if (existingAccessToken != null) {
                if (existingAccessToken.getRefreshToken() != null) {
                    log.info("remove refreshToken! | {}", existingAccessToken.getRefreshToken());
                    refreshToken = existingAccessToken.getRefreshToken();
                    tokenStore.removeRefreshToken(refreshToken);
                }
                log.info("remove existingAccessToken! | {}", existingAccessToken);
                tokenStore.removeAccessToken(existingAccessToken);
            }
        }
    }
}
