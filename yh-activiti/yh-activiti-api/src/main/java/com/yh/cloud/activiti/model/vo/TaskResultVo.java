package com.yh.cloud.activiti.model.vo;

import com.yh.cloud.activiti.model.bo.ProcessDefinitionBo;
import com.yh.cloud.activiti.model.bo.ProcessInstanceBo;
import com.yh.cloud.activiti.model.bo.TaskBo;
import lombok.Data;

import java.util.Map;

/**
 * 工作流task
 *
 * @author yanghan
 * @date 2019/11/1
 */
@Data
public class TaskResultVo {

    private String businessKey;

    private TaskBo taskBo;

    private Map<String, Object> variables;

    private ProcessInstanceBo processInstanceBo;

    private ProcessDefinitionBo processDefinitionBo;
}
