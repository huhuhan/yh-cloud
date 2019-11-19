/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ReturnWrapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.yh.cloud.web.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * 请求响应结果包装对象
 *
 * @author yanghan
 * @date 2019/6/24
 */
@Data
@AllArgsConstructor
public class ReturnWrapper<T> implements Serializable {


    /** 信息 */
    private String message;

    /** 业务状态码 */
    private String code;

    /** 结果数据 */
    private T result;


    ReturnWrapper() {
        this(ReturnCode.SUCCESS);
    }

    protected ReturnWrapper(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public ReturnWrapper(ReturnCode returnCode) {
        this(returnCode.getMessage(), returnCode.getCode());
    }

    public ReturnWrapper(ReturnCode returnCode, T result) {
        this(returnCode.getMessage(), returnCode.getCode(), result);
    }

    /**
     * Sets the 结果数据 , 返回自身的引用.
     *
     * @param result the new 结果数据
     * @return the wrapper
     */
    public ReturnWrapper<T> result(T result) {
        this.setResult(result);
        return this;
    }

}
