package com.yh.common.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.yh.common.log.event.RequestPostEvent;
import com.yh.common.log.event.RequestPreEvent;
import com.yh.common.log.model.entity.RequestModel;
import com.yh.common.log.service.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Objects;

/**
 * 仅参考
 *
 * @author yanghan
 * @date 2022/2/15
 */
public class DefaultRequestLogServiceImpl implements RequestLogService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRequestLogServiceImpl.class);

    @Autowired(required = false)
    private ThreadPoolTaskExecutor threadPoolExecutor;

    @Override
    public void handleRequestPreEvent(RequestPreEvent requestPreEvent) {
        System.err.println("handleRequestPreEvent");
    }

    @Override
    public void handleRequestPostEvent(RequestPostEvent requestPostEvent) {
        System.err.println("handleRequestPostEvent");
        if (Objects.nonNull(threadPoolExecutor)) {
            threadPoolExecutor.submit(() -> this.writeRequestLog(requestPostEvent.getRequestModel()));
        } else {
            this.writeRequestLog(requestPostEvent.getRequestModel());
        }
    }


    private void writeRequestLog(RequestModel requestModel) {
        try {
            // 未配置或出现异常信息，不处理，返回

            // 非处理成功的不做记录，返回

            // 匹配条件不满足跳过，返回

            // 保存数据库日志表
        } catch (Exception e) {
            LOG.error("记录操作日志出错, requestModel: {}", JSON.toJSONString(requestModel), e);
        }
    }
}
