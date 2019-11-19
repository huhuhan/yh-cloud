package com.yh.cloud.activiti.model.vo;

import lombok.Data;

/**
 * @author yanghan
 * @date 2019/11/14
 */
@Data
public class ProcessQueryVo {
    /** 页码 */
    private int pageNum = 0;

    /** 每页数量 */
    private int pageSize = 10;

    /** 定义key  */
    private String processDefinitionKey;

    /** 定义key  */
    private String businessKey;

    private String state;

    /** 激活 */
    public final static String ACTIVE = "active";
    /** 挂起 */
    public final static String SUSPENDED = "suspended";

}
