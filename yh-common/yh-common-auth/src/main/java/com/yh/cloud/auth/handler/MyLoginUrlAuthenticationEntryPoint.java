package com.yh.cloud.auth.handler;

import com.yh.cloud.auth.util.TempUtils;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 扩展：未登录访问，异常处理类
 * 默认实现类 {@link LoginUrlAuthenticationEntryPoint }
 *
 * @author yanghan
 * @date 2020/6/1
 */
@Component
@NoArgsConstructor
public class MyLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 无身份认证对象访问，401需要身份认证
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "未登陆或令牌失效，请重新登录!";
        TempUtils.responseWriter(httpServletResponse,
                TempUtils.tempResult(HttpServletResponse.SC_UNAUTHORIZED, msg, e.getMessage(), null));
    }
}

