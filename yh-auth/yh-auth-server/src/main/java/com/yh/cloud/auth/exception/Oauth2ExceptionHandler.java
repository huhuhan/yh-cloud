package com.yh.cloud.auth.exception;

import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常
 *
 * @author yanghan
 * @date 2020/12/8
 */
@ControllerAdvice
@ResponseBody
public class Oauth2ExceptionHandler {

    @ExceptionHandler(value =  OAuth2Exception.class)
    public ReturnWrapper<Object> handleOauth1(OAuth2Exception e) {
        return ReturnWrapMapper.errorWithException(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = Exception.class)
    public ReturnWrapper<Object> handleOauth2(Exception e) {
        return ReturnWrapMapper.errorWithException(e);
    }
}
