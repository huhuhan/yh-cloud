package com.yh.cloud.activiti.model.bo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 流程定义对象
 * 源码对象ProcessDefinition有个getProcessDefinition()方法，会导致json解析死循环
 *
 * @author yanghan
 * @date 2019/10/30
 */
@Data
public class ProcessDefinitionBo {

    private String id;
    private String category;
    private String name;
    private String key;
    private String description;
    private int version;
    private String resourceName;
    private String deploymentId;
    private String diagramResourceName;
    private boolean hasStartFormKey;
    private boolean hasGraphicalNotation;
    private boolean isSuspended;
    private String tenantId;

    public static ProcessDefinitionBo copyBySource(Object source) {
        ProcessDefinitionBo target = new ProcessDefinitionBo();
        BeanUtils.copyProperties(source, target);
        return target;
    }

}