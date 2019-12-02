package com.yh.cloud.activiti.leave.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yh.cloud.activiti.leave.model.entity.MpBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 请假表
 * </p>
 *
 * @author yanghan
 * @since 2019-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="UserLeave对象", description="请假表")
public class UserLeave extends MpBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "请假理由")
    private String reason;

    @ApiModelProperty(value = "请假时长")
    private Integer days;

    @ApiModelProperty(value = "工作流实例id")
    @TableField("process_instance_Id")
    private String processInstanceId;

    @ApiModelProperty(value = "状态，0：无效，1：有效")
    private Integer status;

}
