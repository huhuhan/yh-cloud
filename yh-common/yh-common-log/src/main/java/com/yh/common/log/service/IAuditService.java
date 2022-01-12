package com.yh.common.log.service;

import com.yh.common.log.model.enrity.Audit;

/**
 * 审计日志接口
 *
 * @author yanghan
 * @date 2022/1/4
 */
public interface IAuditService {
    /**
     * 操作
     *
     * @param audit 日志对象
     */
    void todo(Audit audit);
}
