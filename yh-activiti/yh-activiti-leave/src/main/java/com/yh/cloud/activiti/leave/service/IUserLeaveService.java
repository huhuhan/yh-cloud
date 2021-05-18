package com.yh.cloud.activiti.leave.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.cloud.activiti.leave.model.domain.UserLeave;
import com.yh.common.web.model.entity.CurrentUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 请假表 服务类
 * </p>
 *
 * @author yanghan
 * @since 2019-10-25
 */
public interface IUserLeaveService extends IService<UserLeave> {

    void addUserLeave(CurrentUser currentUser, UserLeave userLeave);

    List<Map<String, Object>> userLeaveList(CurrentUser currentUser);

    void completeLeaveTask(CurrentUser currentUser, Map<String, Object> param);
}
