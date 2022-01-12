package com.yh.common.log.model.properties;

import lombok.Data;

/**
 * 日志切面，请求头参数的自定义键
 * @author yanghan
 * @date 2022/1/4
 */
@Data
public class HeaderKeyPo {
    /** 用户ID */
    private String userId = "yh-user-id";
    /** 用户名称 */
    private String userName = "yh-user-name";
}
