package com.yh.cloud.gateway.model;

import com.yh.cloud.web.wrapper.IReturnCode;

/**
 * @author yanghan
 * @date 2020/11/27
 */
public enum GateWayReturnCode implements IReturnCode {

    SERVICE_UNAVAILABLE("SERVICE UNAVAILABLE", "服务不可用！", "S500");

    private String message;
    private String messageZn;
    private String code;

    GateWayReturnCode(String message, String messageZn, String code) {
        this.message = message;
        this.messageZn = messageZn;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getMessageZn() {
        return this.messageZn;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
