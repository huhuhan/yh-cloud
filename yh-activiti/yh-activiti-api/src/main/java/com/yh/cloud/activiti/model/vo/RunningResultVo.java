package com.yh.cloud.activiti.model.vo;

import com.yh.cloud.activiti.model.bo.ProcessDefinitionBo;
import com.yh.cloud.activiti.model.bo.ProcessInstanceBo;
import com.yh.cloud.activiti.model.bo.TaskBo;
import lombok.Data;

/**
 * 工作流task
 * @author yanghan
 * @date 2019/11/1
 */
@Data
public class RunningResultVo {

    private String businessKey;

    private TaskBo taskBo;

    private ProcessInstanceBo processInstanceBo;

    private ProcessDefinitionBo processDefinitionBo;
}
