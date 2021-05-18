package com.yh.common.auth.token;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

/**
 * 令牌扩展信息接口
 *
 * @author yanghan
 * @date 2021/2/2
 */
public interface TokenEnhancerInfoService {
    Map<String, Object> getTokenInfo(OAuth2Authentication oAuth2Authentication);
}
