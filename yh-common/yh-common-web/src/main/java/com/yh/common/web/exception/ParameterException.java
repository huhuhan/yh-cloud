package com.yh.common.web.exception;

import com.yh.common.web.wrapper.ReturnCode;

/**
 * @author yanghan
 * @date 2019/6/26
 */
public class ParameterException extends BusinessException {

    private static final long serialVersionUID = -995417249630826674L;

    public ParameterException(String parameter) {
        super(ReturnCode.PARAMETER_ERROR.getMessage() + "：" + parameter, ReturnCode.PARAMETER_ERROR.getCode());
    }
}
