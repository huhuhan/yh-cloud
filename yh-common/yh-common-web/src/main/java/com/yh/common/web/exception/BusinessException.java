package com.yh.common.web.exception;

import com.yh.common.web.wrapper.IReturnCode;
import lombok.Data;

/**
 * @author yanghan
 * @date 2019/6/26
 */
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -995417249630826674L;

    private String code;

    public BusinessException(IReturnCode returnCode){
        super(returnCode.getMessage());
        this.code = returnCode.getCode();
    }

    public BusinessException(String message, String code){
        super(message);
        this.code = code;
    }
}
