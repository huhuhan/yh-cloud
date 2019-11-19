package com.yh.cloud.activiti.service;

import com.beust.jcommander.internal.Lists;
import com.yh.cloud.activiti.model.bo.ProcessDefinitionBo;
import com.yh.cloud.activiti.model.bo.ProcessInstanceBo;
import com.yh.cloud.activiti.model.bo.TaskBo;
import com.yh.cloud.activiti.model.vo.CompleteTaskVo;
import com.yh.cloud.activiti.model.vo.StartProcessInstanceVo;
import com.yh.cloud.activiti.model.vo.TaskResultVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.HMProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yanghan
 * @date 2019/11/6
 */
@Slf4j
@Service
public class WorkFlowService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 启动流程实例
     *
     * @param startProcessInstanceVo
     * @return
     */
    public ProcessInstance start(StartProcessInstanceVo startProcessInstanceVo) {
        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(startProcessInstanceVo.getUserId());

            processInstance = runtimeService.startProcessInstanceByKey(
                    startProcessInstanceVo.getProcessDefinitionKey(),
                    startProcessInstanceVo.getBusinessKey(),
                    startProcessInstanceVo.getVariables());

            log.debug("start process : {}", startProcessInstanceVo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        return processInstance;
    }

    /**
     * 查询代办任务
     *
     * @param userId
     * @return
     */
    public List<TaskResultVo> taskList(String userId) {
        List<TaskResultVo> taskResultVos = Lists.newArrayList();

        // 根据当前人的ID查询候选人和代理人
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            if (processInstance == null) {
                continue;
            }
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }

            Map<String, Object> variables = taskService.getVariables(task.getId());


            TaskResultVo taskResultVo = new TaskResultVo();
            taskResultVo.setVariables(variables);
            taskResultVo.setBusinessKey(businessKey);
            taskResultVo.setTaskBo(TaskBo.copyBySource(task));
            taskResultVo.setProcessInstanceBo(ProcessInstanceBo.copyBySource(processInstance));
            taskResultVo.setProcessDefinitionBo(this.getProcessDefinitionBoById(processInstance.getProcessDefinitionId()));
            taskResultVos.add(taskResultVo);
        }
        return taskResultVos;
    }

    public ProcessDefinitionBo getProcessDefinitionBoById(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return ProcessDefinitionBo.copyBySource(processDefinition);
    }


    /**
     * 签收任务
     *
     * @param userId
     * @param taskId
     */
    public void taskClaim(String userId, String taskId) {
        taskService.claim(taskId, userId);
    }

    /**
     * 完成任务
     *
     * @param completeTaskVo
     */
    public void taskComplete(CompleteTaskVo completeTaskVo) {
        taskService.complete(completeTaskVo.getTaskId(), completeTaskVo.getVariables());
    }

    /**
     * 跟踪节点图片，流对象
     *
     * @param processInstanceId
     * @param needCurrent
     * @return
     */
    public InputStream generateStream(String processInstanceId, boolean needCurrent) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        List<String> executedActivityIdList = new ArrayList<String>();
        List<String> currentActivityIdList = new ArrayList<>();
        List<HistoricActivityInstance> historicActivityInstanceList = new ArrayList<>();
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            if (needCurrent) {
                currentActivityIdList = this.runtimeService.getActiveActivityIds(processInstance.getId());
            }
        }
        if (historicProcessInstance != null) {
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            historicActivityInstanceList =
                    historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                executedActivityIdList.add(activityInstance.getActivityId());
            }
        }

        if (StringUtils.isEmpty(processDefinitionId) || executedActivityIdList.isEmpty()) {
            return null;
        }

        //高亮线路id集合
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        List<String> highLightedFlows = this.getHighLightedFlows(definitionEntity, historicActivityInstanceList);

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        //List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        HMProcessDiagramGenerator diagramGenerator = (HMProcessDiagramGenerator) processEngineConfiguration.getProcessDiagramGenerator();
        //List<String> activeIds = this.runtimeService.getActiveActivityIds(processInstance.getId());

        InputStream imageStream = diagramGenerator.generateDiagram(
                bpmnModel, "png",
                executedActivityIdList, highLightedFlows,
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                "宋体",
                null, 1.0, currentActivityIdList);

        return imageStream;
    }

    /**
     * 获取节点高亮线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<String>();
        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 得到节点定义的详细信息
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances
                            .get(i)
                            .getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances
                            .get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

    public Map<String, Object> getTaskVariables(String taskId) {
        return taskService.getVariables(taskId);
    }
}
