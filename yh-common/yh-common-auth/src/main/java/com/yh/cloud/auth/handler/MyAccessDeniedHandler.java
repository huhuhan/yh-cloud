package com.yh.cloud.auth.handler;

import com.yh.cloud.auth.util.TempUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 扩展：无权访问，异常处理类
 * 默认实现类 {@link AccessDeniedHandlerImpl }
 *
 * @author yanghan
 * @date 2020/6/1
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 已登录无权访问，403正确请求但拒绝请求
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String msg = "权限不足，不可访问，请联系管理员!";
        TempUtils.responseWriter(httpServletResponse,
                TempUtils.tempResult(HttpServletResponse.SC_FORBIDDEN, msg, e.getMessage(), null));
    }
}
