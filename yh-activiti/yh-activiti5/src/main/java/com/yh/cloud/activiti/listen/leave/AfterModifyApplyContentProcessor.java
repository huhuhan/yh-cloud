package com.yh.cloud.activiti.listen.leave;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调整请假内容处理器
 *  todo：可改成通用流程事件
 * @author HenryYan
 */
@Component
@Transactional
@Slf4j
public class AfterModifyApplyContentProcessor implements TaskListener {

    private static final long serialVersionUID = 1L;

//    @Autowired
//    LeaveManager leaveManager;

    @Autowired
    RuntimeService runtimeService;

    /* (non-Javadoc)
     * @see org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate.DelegateTask)
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("------监听器：AfterModifyApplyContentProcessor");
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //todo: 业务处理，可通过中间件等方式异步处理
//        Leave leave = leaveManager.getLeave(new Long(processInstance.getBusinessKey()));
//        leave.setLeaveType((String) delegateTask.getVariable("leaveType"));
//        leave.setStartTime((Date) delegateTask.getVariable("startTime"));
//        leave.setEndTime((Date) delegateTask.getVariable("endTime"));
//        leave.setReason((String) delegateTask.getVariable("reason"));
//        leaveManager.saveLeave(leave);
    }

}
