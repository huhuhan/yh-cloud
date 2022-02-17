package com.yh.common.log.service.impl;

import com.yh.common.log.model.entity.Audit;
import com.yh.common.log.service.IAuditService;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

/**
 * 打印日志
 *
 * @author yanghan
 * @date 2022/1/4
 */
@Slf4j
public class LoggerAuditServiceImpl implements IAuditService {
    private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}";

    /**
     * 格式为：{时间}|{应用名}|{类名}|{方法名}|{用户id}|{用户名}|{操作信息}
     * 例子：2022-01-04 14:17:26.084|homestead|com.yh.common.log.service.impl.LoggerAuditServiceImpl|save|1|admin|新增用户:admin
     */
    @Override
    public void todo(Audit audit) {
        log.debug(MSG_PATTERN
                , audit.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                , audit.getApplicationName(), audit.getClassName(), audit.getMethodName()
                , audit.getUserId(), audit.getUserName()
                , audit.getOperation());
    }
}
