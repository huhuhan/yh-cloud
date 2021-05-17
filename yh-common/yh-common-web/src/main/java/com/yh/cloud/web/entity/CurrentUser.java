package com.yh.cloud.web.entity;

import lombok.Data;

/**
 * 当前登录用户
 * @author yanghan
 * @date 2019/6/28
 */
@Data
public class CurrentUser {

    /** 用户ID */
    private Long userId;

    /** 用户登录名 */
    private String loginName;

    /** 用户姓名 */
    private String userName;

}
