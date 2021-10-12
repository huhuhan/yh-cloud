package com.yh.common.web.config;

import com.yh.common.web.exception.BusinessException;
import com.yh.common.web.wrapper.ReturnCode;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author yanghan
 * @date 2020/11/4
 */
@Slf4j
@ResponseBody
public class DefaultExceptionAdvice {

    /**
     * 参数验证异常处理，参考@Vaild
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ReturnWrapper handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return ReturnWrapMapper.error(ReturnCode.PARAMETER_ERROR, msg);
    }

    /**
     * 通用业务异常处理
     *
     * @param e
     * @return ReturnWrapper
     */
    @ExceptionHandler(value = BusinessException.class)
    public ReturnWrapper handleBusinessException(BusinessException e) {
        return this.defHandler(e.getMessage(), e);
    }

    /**
     * 所有异常统一处理
     *
     * @param e
     * @return ReturnCode.ERROR
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ReturnWrapper handleException(Exception e) {
        return this.defHandler(e);
    }

    private ReturnWrapper defHandler(String msg, Exception e) {
        log.error(msg, e);
        return ReturnWrapMapper.errorWithException(e);
    }

    private ReturnWrapper defHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ReturnWrapMapper.errorWithException(e);
    }
}
