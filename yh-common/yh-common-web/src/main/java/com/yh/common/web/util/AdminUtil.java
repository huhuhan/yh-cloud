package com.yh.common.web.util;

import com.yh.cloud.base.constant.BaseConstant;
import com.yh.common.web.exception.BusinessException;
import com.yh.common.web.model.entity.CurrentUser;
import com.yh.common.web.wrapper.ReturnCode;

/**
 * @author yanghan
 * @date 2021/10/11
 */
public class AdminUtil {

    public static void checkToDo(CurrentUser currentUser) {
        if (currentUser == null || !BaseConstant.ADMIN_USER_ID.equals(currentUser.getUserId())) {
            throw new BusinessException(ReturnCode.UNAUTHORIZED_OPERATION);
        }
    }

    public static void unAuthorized() {
        throw new BusinessException(ReturnCode.UNAUTHORIZED_OPERATION);
    }
}
