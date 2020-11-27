package com.yh.cloud.gateway.rest;

import com.yh.cloud.gateway.model.GateWayReturnCode;
import com.yh.cloud.web.wrapper.IReturnCode;
import com.yh.cloud.web.wrapper.ReturnWrapMapper;
import com.yh.cloud.web.wrapper.ReturnWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 断路器处理
 *
 * @author yanghan
 * @date 2020/11/24
 */
@RestController
public class FallbackController {
    @RequestMapping("/fallback")
    public ReturnWrapper<IReturnCode> fallback() {
        return ReturnWrapMapper.error(GateWayReturnCode.SERVICE_UNAVAILABLE);
    }

}
