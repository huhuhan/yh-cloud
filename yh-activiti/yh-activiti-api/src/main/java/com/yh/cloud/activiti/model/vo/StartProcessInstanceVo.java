package com.yh.cloud.activiti.model.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanghan
 * @date 2019/11/6
 */
@Data
@ToString
public class StartProcessInstanceVo implements Serializable {

    private static final long serialVersionUID = -4775276934772353735L;

    private String processDefinitionKey;

    private String businessKey;

    private String userId;
    //todo：可存业务对象，定义key=baseTask
    private Map<String, Object> variables;

    public StartProcessInstanceVo() {
        this.variables = new HashMap<>();
    }
}
