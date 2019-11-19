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
public class CompleteTaskVo implements Serializable {

    private static final long serialVersionUID = -2481159813144925718L;

    private String taskId;

    private Map<String, Object> variables;

    public CompleteTaskVo() {
        this.variables = new HashMap<>();
    }

    public Map<String, Object> put(String key, Object object) {
        this.variables.put(key, object);
        return this.variables;
    }
}
