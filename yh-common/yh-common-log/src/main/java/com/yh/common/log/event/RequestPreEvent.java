package com.yh.common.log.event;

import com.yh.common.log.model.entity.RequestModel;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author yanghan
 * @date 2022/2/15
 */
public class RequestPreEvent extends ApplicationEvent {

    private static final long serialVersionUID = -5839760040474841130L;

    public RequestPreEvent(RequestModel source) {
        super(source);
    }

    public RequestModel getRequestModel() {
        return (RequestModel) source;
    }
}
