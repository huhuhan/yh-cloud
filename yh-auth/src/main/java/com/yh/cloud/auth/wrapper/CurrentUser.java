package com.yh.cloud.auth.wrapper;

import lombok.Data;

/**
 * 当前用户
 *
 * @author yanghan
 * @date 2019/7/2
 */
@Data
public class CurrentUser {
    private String id;

    private String userName;

    private String passWord;

    private String[] roles;

}
