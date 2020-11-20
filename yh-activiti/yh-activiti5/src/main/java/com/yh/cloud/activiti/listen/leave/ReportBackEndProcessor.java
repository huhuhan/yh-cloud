package com.yh.cloud.activiti.listen.leave;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 销假后处理器
 *  todo：可改成通用流程事件。如流程终止
 * <p>设置销假时间</p>
 * <p>使用Spring代理，可以注入Bean，管理事物</p>
 *
 * @author HenryYan
 */
@Component
@Transactional
@Slf4j
public class ReportBackEndProcessor implements TaskListener {

    private static final long serialVersionUID = 1L;

    /*@Autowired
    LeaveManager leaveManager;*/

    @Resource
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
//        Object realityStartTime = delegateTask.getVariable("realityStartTime");
//        leave.setRealityStartTime((Date) realityStartTime);
//        Object realityEndTime = delegateTask.getVariable("realityEndTime");
//        leave.setRealityEndTime((Date) realityEndTime);
//        leaveManager.saveLeave(leave);
    }

}
