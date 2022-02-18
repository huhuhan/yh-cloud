package com.yh.common.log.filter;

import cn.hutool.core.util.StrUtil;
import com.yh.common.log.model.constant.MDCConstant;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


/**
 * 生成traceId追踪
 *
 * @author yanghan
 * @date 2022/2/18
 */
public class RequestTraceFilter extends OncePerRequestFilter {

    /**
     * trace id name
     */
    private final String traceIdName;

    public RequestTraceFilter(String traceIdName) {
        this.traceIdName = traceIdName;
    }

    private String getTraceId(HttpServletRequest request) {
        String traceId;
        if (StrUtil.isNotEmpty(traceId = MDC.get(traceIdName))) {
            return traceId;
        }
        if (StrUtil.isNotEmpty(traceId = request.getHeader(traceIdName))) {
            return traceId;
        }
        if (StrUtil.isNotEmpty(traceId = request.getParameter(traceIdName))) {
            return traceId;
        }
        return UUID.randomUUID().toString().replace("-", StrUtil.EMPTY);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        MDC.put(MDCConstant.TRACE_ID, getTraceId(httpServletRequest));
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            MDC.remove(MDCConstant.TRACE_ID);
        }
    }
}
