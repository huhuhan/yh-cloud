package com.yh.common.log.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.util.TypeUtils;
import com.yh.common.log.config.AppFactory;
import com.yh.common.log.config.RequestLogProperties;
import com.yh.common.log.event.RequestPostEvent;
import com.yh.common.log.event.RequestPreEvent;
import com.yh.common.log.model.ICurrentUser;
import com.yh.common.log.model.constant.MDCConstant;
import com.yh.common.log.model.entity.RequestModel;
import com.yh.common.log.service.CurrentUserLogService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yanghan
 * @date 2022/2/15
 */
@Slf4j
public class RequestAspect implements InitializingBean, MethodInterceptor {

    private final RequestLogProperties requestLogProperties;
    private final CurrentUserLogService currentUserLogService;
    /**
     * 字段脱敏过滤器
     */
    private ValueFilter[] valueFilters;

    public RequestAspect(RequestLogProperties requestLogProperties, CurrentUserLogService currentUserLogService) {
        this.requestLogProperties = requestLogProperties;
        this.currentUserLogService = currentUserLogService;
    }

    @Override
    public void afterPropertiesSet() {
        valueFilters = new ValueFilter[]{
                (object, name, value) -> CollUtil.contains(requestLogProperties.getSensitiveFields(), name) ? "-" : value
        };
    }

    protected HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.isNull(requestAttributes) ? null : requestAttributes.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return Objects.requireNonNull(requestAttributes).getResponse();
    }

    private Map<String, String[]> getRequestHeader(HttpServletRequest request) {
        Map<String, String[]> headers = new HashMap<>(16);
        List<String> headerValues = new ArrayList<>();
        final String[] emptyStringArray = new String[0];
        for (Enumeration<String> headerNameEnumeration = request.getHeaderNames(); headerNameEnumeration.hasMoreElements(); ) {
            String headerName = headerNameEnumeration.nextElement();
            headerValues.clear();
            for (Enumeration<String> headerValueEnumeration = request.getHeaders(headerName); headerValueEnumeration.hasMoreElements(); ) {
                headerValues.add(headerValueEnumeration.nextElement());
            }
            headers.put(headerName, headerValues.toArray(emptyStringArray));
        }
        return headers;
    }

    private RequestModel createRequestModel(MethodInvocation methodInvocation, HttpServletRequest request) {
        RequestModel requestModel = new RequestModel();
        requestModel.setUrl(request.getRequestURL().toString());
        requestModel.setPathPattern((String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"));
        requestModel.setTraceId(this.getTraceId());
        requestModel.setCurrentUser(currentUserLogService.getCurrentUser());
        requestModel.setClientIP(this.getClientIP(request));
        requestModel.setRequestMethod(request.getMethod());
        requestModel.setRequestTime(new Date());
        requestModel.setRequestHeader(this.getRequestHeader(request));
        requestModel.setRequestParam(new HashMap<>(request.getParameterMap()));

        final Method method = methodInvocation.getMethod();
        final Object[] arguments = methodInvocation.getArguments();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0, l = parameterAnnotations.length; i < l; i++) {
            if (ArrayUtil.isNotEmpty(parameterAnnotations[i])) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (Objects.isNull(requestModel.getRequestBody()) && annotation instanceof RequestBody) {
                        requestModel.setRequestBody(JSON.toJSON(arguments[i]));
                    } else if (annotation instanceof PathVariable) {
                        PathVariable pathVariable = (PathVariable) annotation;
                        requestModel.getRequestParam().put(StrUtil.nullToDefault(pathVariable.value(), pathVariable.name()), new String[]{TypeUtils.castToString(arguments[i])});
                    }
                }
            }
        }
        return requestModel;
    }

    private String getClientIP(HttpServletRequest request) {
        String clientIP = ServletUtil.getClientIP(request);
        return "0:0:0:0:0:0:0:1".equals(clientIP) ? "127.0.0.1" : clientIP;
    }

    private String getTraceId() {
        String traceId = MDC.get(MDCConstant.TRACE_ID);
        if (StrUtil.isNotEmpty(traceId)) {
            return traceId;
        }
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HttpServletRequest request = this.getHttpServletRequest();
        RequestModel requestModel = this.createRequestModel(invocation, request);
        this.doBefore(requestModel);
        Throwable throwable = null;
        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Throwable e) {
            throwable = e;
        }
        requestModel.setResponseTime(new Date());
        requestModel.setDurationMs(requestModel.getResponseTime().getTime() - requestModel.getRequestTime().getTime());

        // 异常返回
        if (Objects.nonNull(throwable)) {
            requestModel.setException(ExceptionUtil.getMessage(throwable));
            requestModel.setResponseState(HttpStatus.INTERNAL_SERVER_ERROR.value());
            this.doAfter(requestModel);
            throw throwable;
        }

        // 正常信息返回
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            if (!(responseEntity.getBody() instanceof byte[])) {
                requestModel.setResponseBody(responseEntity.getBody());
            }
            requestModel.setResponseState(responseEntity.getStatusCodeValue());
        } else {
            requestModel.setResponseState(this.getHttpServletResponse().getStatus());
            requestModel.setResponseBody(result);
        }
        this.doAfter(requestModel);
        return result;
    }

    /**
     * 执行前
     */
    protected void doBefore(RequestModel requestModel) {
        this.logRequest(requestModel, false);
        AppFactory.publishEvent(new RequestPreEvent(requestModel));
    }

    /**
     * 执行后
     */
    protected void doAfter(RequestModel requestModel) {
        // 注销换账号重新登录缓存问题重新赋值
        requestModel.setCurrentUser(currentUserLogService.getCurrentUser());
        this.logRequest(requestModel, true);
        AppFactory.publishEvent(new RequestPostEvent(requestModel));
    }

    private void logRequest(RequestModel requestModel, boolean isResponse) {
        if (!log.isInfoEnabled() || PatternMatchUtils.simpleMatch(requestLogProperties.getIgnoreUrls(), requestModel.getPathPattern())) {
            return;
        }
        StringBuilder append;
        if (isResponse) {
            // ******** response log ********
            append = new StringBuilder(StrUtil.center(" response log ", 30, "*"));
        } else {
            // ******** request log ********
            append = new StringBuilder(StrUtil.center(" request log ", 30, "*"));
        }
        append.append('\n').append("==>       trace id: ").append(requestModel.getTraceId());
        append.append('\n').append("==>       username: ").append(Optional.ofNullable(requestModel.getCurrentUser()).map(ICurrentUser::getUsername).orElse(StrUtil.EMPTY));
        append.append('\n').append("==>   request time: ").append(DateUtil.format(requestModel.getRequestTime(), DatePattern.NORM_DATETIME_MS_PATTERN));
        append.append('\n').append("==>    request url: ").append(requestModel.getUrl());
        append.append('\n').append("==>   path pattern: ").append(requestModel.getPathPattern());
        append.append('\n').append("==>     request ip: ").append(requestModel.getClientIP());
        append.append('\n').append("==> request method: ").append(requestModel.getRequestMethod());
        append.append('\n').append("==> request header: ").append(toJSONString(requestModel.getRequestHeader()));
        append.append('\n').append("==>  request param: ").append(toJSONString(requestModel.getRequestParam()));
        append.append('\n').append("==>   request body: ").append(toJSONString(requestModel.getRequestBody()));

        // response log
        if (isResponse) {
            append.append('\n').append("==> response state: ").append(requestModel.getResponseState());
            append.append('\n').append("==>  response body: ").append(toJSONString(requestModel.getResponseBody()));
            append.append('\n').append("==>  response time: ").append(DateUtil.format(requestModel.getResponseTime(), DatePattern.NORM_DATETIME_MS_PATTERN));
            append.append('\n').append("==>      exception: ").append(StrUtil.nullToEmpty(requestModel.getException()));
            append.append('\n').append("==>    duration ms: ").append(requestModel.getDurationMs());
        }
        log.info("\n{}", append);
    }

    private String toJSONString(Object value) {
        if (Objects.isNull(value)) {
            return StrUtil.EMPTY;
        }
        String valueString;
        if (value instanceof CharSequence) {
            valueString = value.toString();
        } else {
            valueString = JSON.toJSONString(value, SerializeConfig.globalInstance, valueFilters, DatePattern.NORM_DATETIME_PATTERN, JSON.DEFAULT_GENERATE_FEATURE);
        }
        int fieldLogLength = requestLogProperties.getFieldLogLength();
        if (fieldLogLength < 1 || StrUtil.length(valueString) < fieldLogLength) {
            return valueString;
        }
        return valueString.substring(0, fieldLogLength) + "... ...";
    }
}
