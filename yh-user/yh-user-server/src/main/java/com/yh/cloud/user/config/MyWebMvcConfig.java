package com.yh.cloud.user.config;

import com.yh.common.web.config.DefaultWebMvcConfig;
import org.springframework.context.annotation.Configuration;

/**
 * MVC配置
 * @author yanghan
 * @date 2021/9/23
 */
@Configuration
public class MyWebMvcConfig extends DefaultWebMvcConfig {

    //引入 @CUser 注解获取当前登录用户（注意，需要经过网关请求才可解析）
}
