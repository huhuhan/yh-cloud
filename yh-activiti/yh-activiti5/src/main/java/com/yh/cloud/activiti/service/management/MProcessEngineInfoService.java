package com.yh.cloud.activiti.service.management;

import com.yh.common.web.wrapper.ReturnMsg;
import org.activiti.engine.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Activitiy引擎信息管理
 *
 * @author yanghan
 * @date 2019/10/31
 */
@Service
public class MProcessEngineInfoService {

    @Autowired
    private ManagementService managementService;

    public ReturnMsg info() {
        Map<String, String> engineProperties = managementService.getProperties();

        Map<String, String> systemProperties = new HashMap<String, String>();
        Properties systemProperties11 = System.getProperties();
        Set<Object> objects = systemProperties11.keySet();
        for (Object object : objects) {
            systemProperties.put(object.toString(), systemProperties11.get(object.toString()).toString());
        }

        return new ReturnMsg<>()
                .keyValue("engineProperties", engineProperties)
                .keyValue("systemProperties", systemProperties);
    }

}
