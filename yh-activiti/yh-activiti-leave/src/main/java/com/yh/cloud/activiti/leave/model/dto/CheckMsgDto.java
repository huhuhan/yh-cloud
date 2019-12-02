package com.yh.cloud.activiti.leave.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审核信息
 * @author yanghan
 * @date 2019/11/12
 */
@Data
public class CheckMsgDto {

    private String userId;

    private String userName;

    private String checkMsg;

    private LocalDateTime checkTime;
}
