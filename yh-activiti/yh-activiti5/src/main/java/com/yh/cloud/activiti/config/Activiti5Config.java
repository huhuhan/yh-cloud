package com.yh.cloud.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author yanghan
 * @date 2019/10/28
 */
@Component
public class Activiti5Config implements ProcessEngineConfigurationConfigurer {
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");

        //是否使用activiti自带用户组织表，如果是，这里为true，如果不是，这里为false
//        processEngineConfiguration.setDbIdentityUsed(true);
    }

}