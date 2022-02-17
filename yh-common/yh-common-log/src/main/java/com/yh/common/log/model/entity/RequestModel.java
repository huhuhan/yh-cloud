package com.yh.common.log.model.entity;

import com.yh.common.log.model.ICurrentUser;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author yanghan
 * @date 2022/2/17
 */
@Data
public class RequestModel implements java.io.Serializable {

    private static final long serialVersionUID = 7814837643442599727L;

    /**
     * url
     */
    private String url;

    /**
     * path pattern
     */
    private String pathPattern;

    /**
     * trace id
     */
    private String traceId;

    /**
     * 账户
     */
    private ICurrentUser currentUser;

    /**
     * client ip
     */
    private String clientIP;

    /**
     * request method
     */
    private String requestMethod;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 请求头
     */
    private Map<String, String[]> requestHeader;

    /**
     * 请求参数
     */
    private Map<String, String[]> requestParam;

    /**
     * 请求体
     */
    private Object requestBody;

    /**
     * 响应体
     */
    private Object responseBody;

    /**
     * 响应时间
     */
    private Date responseTime;

    /**
     * 响应状态
     */
    private Integer responseState;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 耗时
     */
    private Long durationMs;


}