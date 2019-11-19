package com.yh.cloud.web.wrapper;

import lombok.Getter;

/**
 * @author yanghan
 * @date 2019/6/28
 */
@Getter
//@AllArgsConstructor
public enum ReturnCode {
    SUCCESS("Success", "成功", "S200"),
    ERROR("Error", "失败", "S500"),
    ILLEGAL_PARAMETER("Invalid Parameter", "非法参数", "S400"),
    NO_IN("Invalid Token", "没有权限", "S403"),


    /** 业务响应枚举 *********************/
    PARAMETER_ERROR("\"%s\" Parameter Missing", "%s参数丢失", "0001E");
    /** Demo1业务, 暂定【0100E~0199E】100种 */

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


}
