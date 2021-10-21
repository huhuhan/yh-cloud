package com.yh.cloud.base.constant;

/**
 * 系统全局常量类
 *
 * @author yanghan
 * @date 2019/7/29
 */
public interface BaseConstant {
    int MAX_PAGE_SIZE = 99999;

    /** 统一全局前缀 */
    String GLOBAL_PREFIX = "yh_";

    String TEMP_CURRENT_USER = "currentUser";

    String HEADER_CURRENT_USER = GLOBAL_PREFIX + "current-user";
    String HEADER_CURRENT_USER_ID = GLOBAL_PREFIX + "user_id";
    String HEADER_CURRENT_TENANT_ID = GLOBAL_PREFIX + "tenant_id";

    /** 管理员用户ID */
    String ADMIN_USER_ID = "50831";
    /** 默认密码 */
    String DEFAULT_PWD = "a123456";
}
