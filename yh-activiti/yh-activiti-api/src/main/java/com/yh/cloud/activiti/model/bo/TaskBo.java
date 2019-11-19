package com.yh.cloud.activiti.model.bo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 任务对象
 *
 * @author yanghan
 * @date 2019/11/6
 */
@Data
public class TaskBo {
    private String id;
    private int revision;
    private String owner;
    private String assignee;
    private String initialAssignee;
    private String parentTaskId;
    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;
    private int priority = 50;
    private Date createTime;
    private Date dueDate;
    private int suspensionState;
    private String category;
    private boolean isIdentityLinksInitialized;
    private String executionId;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private String formKey;
    private boolean isDeleted;
    private String eventName;
    private String tenantId;
    private boolean forcedUpdate;

    public static TaskBo copyBySource(Object source) {
        TaskBo target = new TaskBo();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
