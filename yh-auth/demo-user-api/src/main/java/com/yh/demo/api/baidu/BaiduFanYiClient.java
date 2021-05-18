package com.yh.demo.api.baidu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 第三方接口例子
 * @author yanghan
 * @date 2021/3/23
 */
@FeignClient(name = "baidu", contextId = "fanyi", url = "https://fanyi.baidu.com")
public interface BaiduFanYiClient {

    @PostMapping(value = "/sug", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Object sug(@RequestParam Map<String, Object> form);
}
