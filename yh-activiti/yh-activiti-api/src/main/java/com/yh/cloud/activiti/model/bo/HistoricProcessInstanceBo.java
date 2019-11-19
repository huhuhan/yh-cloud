package com.yh.cloud.activiti.model.bo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 历史流程实例对象
 *
 * @author yanghan
 * @date 2019/11/6
 */
@Data
public class HistoricProcessInstanceBo {
    private String id;
    private String processInstanceId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private String deploymentId;
    private Date startTime;
    private Date endTime;
    private Long durationInMillis;
    private String deleteReason;

    private String endActivityId;
    private String businessKey;
    private String startUserId;
    private String startActivityId;
    private String superProcessInstanceId;
    private String tenantId = "";
    private String name;
    private String localizedName;
    private String description;
    private String localizedDescription;

    public static HistoricProcessInstanceBo copyBySource(Object source) {
        HistoricProcessInstanceBo target = new HistoricProcessInstanceBo();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
