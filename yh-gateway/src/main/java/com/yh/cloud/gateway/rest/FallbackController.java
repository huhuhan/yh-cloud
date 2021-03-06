package com.yh.cloud.gateway.rest;

import com.yh.common.web.wrapper.IReturnCode;
import com.yh.common.web.wrapper.ReturnCode;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
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
        return ReturnWrapMapper.error(ReturnCode.SERVICE_UNAVAILABLE);
    }

}
