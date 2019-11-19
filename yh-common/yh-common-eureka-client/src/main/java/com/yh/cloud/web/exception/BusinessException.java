package com.yh.cloud.web.exception;

import com.yh.cloud.web.wrapper.ReturnCode;
import lombok.Data;

/**
 * @author yanghan
 * @date 2019/6/26
 */
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -995417249630826674L;

    private String code;

    public BusinessException(ReturnCode returnCode){
        super(returnCode.getMessage());
        this.code = returnCode.getCode();
    }

    public BusinessException(String message, String code){
        super(message);
        this.code = code;
    }
}
