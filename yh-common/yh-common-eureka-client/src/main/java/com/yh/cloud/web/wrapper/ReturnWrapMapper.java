/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ReturnWrapMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.yh.cloud.web.wrapper;

import com.yh.cloud.web.exception.BusinessException;

/**
 * 响应结果操作类
 *
 * @author yanghan
 * @date 2019/6/24
 */
public class ReturnWrapMapper {

    /**
     * Wrap ERROR. code=100
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ReturnWrapper<E> illegalParameter() {
        return new ReturnWrapper<>(ReturnCode.ILLEGAL_PARAMETER);
    }

    public static <E> ReturnWrapper<E> error(BusinessException e) {
        return new ReturnWrapper<>(e.getMessage(), e.getCode());
    }

    /**
     * Wrap ERROR. code=500
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ReturnWrapper<E> error() {
        return new ReturnWrapper<>(ReturnCode.ERROR);
    }


    /**
     * Error wrapper.
     *
     * @param <E> the type parameter
     * @return the wrapper
     */
    public static <E> ReturnWrapper<E> error(ReturnCode error) {
        return new ReturnWrapper<>(error);
    }


    /**
     * Error wrapper.
     *
     * @param <E> the type parameter
     * @return the wrapper
     */
    public static <E> ReturnWrapper<E> errorWithException(Exception e) {


        return new ReturnWrapper<>(e.getMessage(), ReturnCode.ERROR.getCode());
    }

    /**
     * Wrap SUCCESS. code=200
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ReturnWrapper<E> ok() {
        return new ReturnWrapper<>(ReturnCode.SUCCESS);
    }

    /**
     * Ok wrapper.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the wrapper
     */
    public static <E> ReturnWrapper<E> ok(E o) {
        return new ReturnWrapper<>(ReturnCode.SUCCESS, o);
    }
}
