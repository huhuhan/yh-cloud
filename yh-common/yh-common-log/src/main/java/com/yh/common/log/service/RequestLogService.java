package com.yh.common.log.service;

import com.yh.common.log.event.RequestPostEvent;
import com.yh.common.log.event.RequestPreEvent;
import org.springframework.context.event.EventListener;

/**
 * 请求日志事件处理接口
 *
 * @author yanghan
 * @date 2022/2/15
 */
public interface RequestLogService {

    /**
     * 前置事件处理
     *
     * @param requestPreEvent
     * @return void
     * @author yanghan
     * @date 2022/2/15
     */
    @EventListener(RequestPreEvent.class)
    void handleRequestPreEvent(RequestPreEvent requestPreEvent);


    /**
     * 后置事件处理
     *
     * @param requestPostEvent
     * @return void
     * @author yanghan
     * @date 2022/2/15
     */
    @EventListener(RequestPostEvent.class)
    void handleRequestPostEvent(RequestPostEvent requestPostEvent);

}
