package com.yh.common.web.wrapper;

import lombok.Getter;

/**
 * @author yanghan
 * @date 2019/6/28
 */
@Getter
//@AllArgsConstructor
public enum ReturnCode implements IReturnCode {
    /** 系统状态码 */
    SUCCESS("Success", "成功", "S200"),
    ERROR("Error", "失败", "S500"),
    ILLEGAL_PARAMETER("Invalid Parameter", "非法参数", "S400"),
    NO_IN("Invalid Token", "没有权限", "S403"),
    SERVICE_UNAVAILABLE("Service Unavailable", "服务不可用！", "S500"),
    UNAUTHORIZED("Unauthorized", "未登录或Token过期！", "S401"),
    FORBIDDEN("Invalid Token", "没有权限，拒绝访问！", "S403"),

    /** 业务通用状态码 */
    PARAMETER_ERROR("Parameter Missing", "缺失参数", "E001"),
    UNAUTHORIZED_OPERATION("Unauthorized operation", "无权操作", "E002"),

    /** 用户组织状态码 */
    USER_01("User already exists", "用户已存在！", "E1001"),
    USER_02("User does not exist", "用户不存在！", "E1002"),
    USER_03("The original password is incorrect", "原密码不正确！", "E1003"),
    ;


    /**
     * 业务响应对象封装
     *
     * @author yanghan
     * @date 2019/7/4
     */
    ReturnCode(String message, String messageZn, String code) {
        this.message = message;
        this.messageZn = messageZn;
        this.code = code;
    }

    /** 响应消息，英文 */
    private String message;

    /** 响应消息，中文 */
    private String messageZn;

    /**
     * 系统状态码，定义S000起
     * 业务状态码，暂定4位，定义0000E递增
     */
    private String code;

    @Override
    public String getMessage() {
        // 中英文信息
        return true ? messageZn : message;
    }
}
