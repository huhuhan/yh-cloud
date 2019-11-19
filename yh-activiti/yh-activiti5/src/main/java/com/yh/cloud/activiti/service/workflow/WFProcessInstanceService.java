package com.yh.cloud.activiti.service.workflow;

import com.yh.cloud.activiti.model.bo.HistoricProcessInstanceBo;
import com.yh.cloud.activiti.model.bo.ProcessInstanceBo;
import com.yh.cloud.activiti.model.bo.TaskBo;
import com.yh.cloud.activiti.model.vo.FinishedResultVo;
import com.yh.cloud.activiti.model.vo.ProcessQueryVo;
import com.yh.cloud.activiti.model.vo.RunningResultVo;
import com.yh.cloud.activiti.service.WorkFlowService;
import com.yh.cloud.activiti.util.WorkflowUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yh.cloud.web.wrapper.ReturnMsg;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 流程实例管理
 *
 * @author yanghan
 * @date 2019/10/29
 */
@Service
public class WFProcessInstanceService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private HistoryService historyService;

    /**
     * 运行中实例列表
     *
     * @param processQueryVo
     * @return
     */
    public PageInfo runningPSList(ProcessQueryVo processQueryVo) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

        query = (ProcessInstanceQuery) this.initQueryParam(query, processQueryVo);
        query = query.orderByProcessInstanceId().desc();

        List<ProcessInstance> list = query.listPage(
                WorkflowUtils.pageParam2(processQueryVo.getPageNum(), processQueryVo.getPageSize()),
                processQueryVo.getPageSize());

        Page<RunningResultVo> page = new Page(processQueryVo.getPageNum(), processQueryVo.getPageSize());
        list.forEach(processInstance -> {
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                return;
            }
            RunningResultVo runningResultVo = new RunningResultVo();
            runningResultVo.setBusinessKey(businessKey);
            runningResultVo.setProcessInstanceBo(ProcessInstanceBo.copyBySource(processInstance));
//            runningResultVo.setProcessDefinitionBo(workFlowService.getProcessDefinitionBoById(processInstance.getProcessDefinitionId()));
            // 当前任务节点
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstance.getId())
                    .active()
                    .singleResult();
            runningResultVo.setTaskBo(task != null ? TaskBo.copyBySource(task) : null);
            page.add(runningResultVo);
        });
        page.setTotal(query.count());

        return new PageInfo(page);
    }

    public ReturnMsg updateState(String state, String processInstanceId) throws Exception {
        String message;
        if (state.equals("active")) {
            runtimeService.activateProcessInstanceById(processInstanceId);
            message = "已激活ID为[" + processInstanceId + "]的流程实例。";
        } else if (state.equals("suspend")) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            message = "已挂起ID为[" + processInstanceId + "]的流程实例。";
        } else {
            throw new Exception("state: active|suspend");
        }
        return new ReturnMsg<>().keyValue("message", message);
    }

    /**
     * 反射方式初始化query对象，待扩展
     *
     * @param query
     * @param processQueryVo
     * @return
     */
    private Object initQueryParam(Object query, ProcessQueryVo processQueryVo) {
        try {
            if (StringUtils.isNoneEmpty(processQueryVo.getProcessDefinitionKey())) {
                query = MethodUtils.invokeExactMethod(query, "processDefinitionKey", processQueryVo.getProcessDefinitionKey());
            }
            if (StringUtils.isNoneEmpty(processQueryVo.getBusinessKey())) {
                query = MethodUtils.invokeExactMethod(query, "processInstanceBusinessKey", processQueryVo.getBusinessKey());
            }
            if (StringUtils.isNotEmpty(processQueryVo.getState())) {
                if (ProcessQueryVo.ACTIVE.equals(processQueryVo.getState())) {
                    query = MethodUtils.invokeExactMethod(query, "active");
                } else if (ProcessQueryVo.SUSPENDED.equals(processQueryVo.getState())) {
                    query = MethodUtils.invokeExactMethod(query, "suspended");
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 历史实例列表
     *
     * @param processQueryVo
     * @return
     */
    public PageInfo historyPSList(ProcessQueryVo processQueryVo) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        query = (HistoricProcessInstanceQuery) this.initQueryParam(query, processQueryVo);
        query.finished().orderByProcessInstanceEndTime().desc();

        List<HistoricProcessInstance> list = query.listPage(
                WorkflowUtils.pageParam2(processQueryVo.getPageNum(), processQueryVo.getPageSize()),
                processQueryVo.getPageSize());

        Page<FinishedResultVo> page = new Page(processQueryVo.getPageNum(), processQueryVo.getPageSize());

        list.forEach(historicProcessInstance -> {
            String businessKey = historicProcessInstance.getBusinessKey();
            if (businessKey == null) {
                return;
            }
            FinishedResultVo finishedResultVo = new FinishedResultVo();
            finishedResultVo.setBusinessKey(businessKey);
            finishedResultVo.setHistoricProcessInstanceBo(HistoricProcessInstanceBo.copyBySource(historicProcessInstance));
            //todo：可扩展，历史任务的附加信息
            List<HistoricVariableInstance> data = historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
            data.forEach(historicVariableInstance -> {
                Map<String, Object> variable = finishedResultVo.getHistoryVariable();
                variable.put(historicVariableInstance.getVariableName(), ((HistoricVariableInstanceEntity) historicVariableInstance).getCachedValue());
            });
            page.add(finishedResultVo);
        });
        page.setTotal(query.count());

        return new PageInfo(page);
    }

    public void delete(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, "删除流程");
    }
}
