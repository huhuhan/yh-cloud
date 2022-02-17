package com.yh.common.log.event;

import com.yh.common.log.model.entity.RequestModel;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author yanghan
 * @date 2022/2/15
 */
public class RequestPostEvent extends ApplicationEvent {

    private static final long serialVersionUID = -7126075343821953460L;

    public RequestPostEvent(RequestModel source) {
        super(source);
    }

    public RequestModel getRequestModel() {
        return (RequestModel) source;
    }
}
