package com.yh.cloud.activiti.model.vo;

import com.yh.cloud.activiti.model.bo.HistoricProcessInstanceBo;
import com.yh.cloud.activiti.model.bo.ProcessDefinitionBo;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流task
 * @author yanghan
 * @date 2019/11/1
 */
@Data
public class FinishedResultVo {

    private String businessKey;

    private HistoricProcessInstanceBo historicProcessInstanceBo;

    private ProcessDefinitionBo processDefinitionBo;

    private Map<String, Object> historyVariable;

    public Map<String, Object> getHistoryVariable() {
        if(null == this.historyVariable){
            historyVariable = new HashMap<>();
        }
        return historyVariable;
    }
}
