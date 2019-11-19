package com.yh.cloud.activiti.model.bo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * 流程实例对象
 *
 * @author yanghan
 * @date 2019/11/6
 */
@Data
public class ProcessInstanceBo {
    private String id;

    private String processDefinitionId;

    private String processDefinitionName;

    private String processDefinitionKey;

    private Integer processDefinitionVersion;

    private String deploymentId;

    private String businessKey;

    private boolean isSuspended;

    private Map<String, Object> processVariables;

    private String tenantId;

    private String name;

    private String description;

    private String localizedName;

    private String localizedDescription;

    public static ProcessInstanceBo copyBySource(Object source) {
        ProcessInstanceBo tar = new ProcessInstanceBo();
        BeanUtils.copyProperties(source, tar);
        return tar;
    }
}
