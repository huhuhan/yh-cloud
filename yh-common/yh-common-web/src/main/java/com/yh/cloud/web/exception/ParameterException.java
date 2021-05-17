package com.yh.cloud.web.exception;

import com.yh.cloud.web.wrapper.ReturnCode;

/**
 * @author yanghan
 * @date 2019/6/26
 */
public class ParameterException extends BusinessException {

    private static final long serialVersionUID = -995417249630826674L;

    public ParameterException(String parameter) {
        super(String.format(ReturnCode.PARAMETER_ERROR.getMessage(), parameter), ReturnCode.PARAMETER_ERROR.getCode());
    }
}
