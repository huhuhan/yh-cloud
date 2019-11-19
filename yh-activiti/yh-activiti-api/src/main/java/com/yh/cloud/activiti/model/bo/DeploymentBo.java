package com.yh.cloud.activiti.model.bo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 流程部署对象
 *
 * @author yanghan
 * @date 2019/10/30
 */
@Data
public class DeploymentBo {
    private String id;
    private String name;
    private Date deploymentTime;
    private String category;
    private String tenantId;

    public static DeploymentBo copyBySource(Object source) {
        DeploymentBo target = new DeploymentBo();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
