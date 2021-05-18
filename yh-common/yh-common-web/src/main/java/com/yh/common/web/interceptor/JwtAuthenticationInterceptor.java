package com.yh.common.web.interceptor;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yh.common.web.model.entity.CurrentUser;
import com.yh.common.web.util.JwtUtil;
import com.yh.common.web.wrapper.ReturnCode;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt请求拦截器
 *
 * @author yanghan
 * @date 2019/7/31
 */
@Slf4j
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader(JwtUtil.AUTHORIZATION);
        authorization = StringUtils.isEmpty(authorization) ? request.getHeader("token") : authorization;
        if (StringUtils.isEmpty(authorization)) {
            log.error("请求header缺失Authorization凭证");
            doNoAuth(response, ReturnWrapMapper.error(ReturnCode.NO_IN));
            return false;
        }

        String token = authorization.replace(JwtUtil.BEARER, "").trim();
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        if (null == jwt) {
            log.error("无效token");
            doNoAuth(response, ReturnWrapMapper.error(ReturnCode.NO_IN));
            return false;
        }

        CurrentUser currentUser = null;
        //获取当前用户信息
        try {
            String loginName = jwt.getSubject();
            Claim claim = jwt.getClaim(JwtUtil.INFO_KEY);
            String json = claim.asString();
            currentUser = JSONUtil.toBean(json, CurrentUser.class);
            log.info("当前登录用户: {}", loginName);
        } catch (Exception e) {
            log.error("获取当前用户信息失败");
            e.printStackTrace();
        }

        if (null != currentUser) {
            //todo: 后期保存到ThreadLocal
            request.setAttribute("currentUser", currentUser);
            return true;
        }

        doNoAuth(response, ReturnWrapMapper.error(ReturnCode.NO_IN));
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    /**
     * 无访问权限返回
     *
     * @param response
     * @param error
     * @param <E>
     * @throws IOException
     */
    private <E> void doNoAuth(HttpServletResponse response, ReturnWrapper<E> error) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JSONUtil.toJsonStr(error));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
