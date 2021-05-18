package com.yh.demo.api.interceptor;

import com.yh.cloud.base.constant.BaseConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Feign转发拦截器，补充请求头参数
 * @author yanghan
 * @date 2021/3/16
 */
public class FeignInterceptor implements RequestInterceptor {

    protected List<String> requestHeaders = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        requestHeaders.add(BaseConstant.HEADER_CURRENT_TENANT_ID);
        requestHeaders.add(BaseConstant.HEADER_CURRENT_USER_ID);
        requestHeaders.add(BaseConstant.HEADER_CURRENT_TENANT_ID);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            // 注意，hystrix的隔离策略必须为信号量，才保证为同一线程请求
            // todo: 分布式存储请求头信息
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Map<String, String> headers = this.getHeaders(request);

        for (String requestHeader : requestHeaders) {
            String value = headers.get(requestHeader);
            if (null != value) {
                requestTemplate.header(requestHeader, value);
            }
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toString();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private String getBody(HttpServletRequest request) {
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuilder body = new StringBuilder();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                body.append(name).append("=").append(values).append("&");
            }
        }
        if (body.length() != 0) {
            body.deleteCharAt(body.length() - 1);
        }
        return body.toString();
    }

}
