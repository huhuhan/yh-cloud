package com.yh.cloud.web.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yanghan
 * @date 2019/7/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页参数")
public class PageQueryVo {

    @ApiModelProperty("页码")
    private int pageNum = 1;

    @ApiModelProperty("每页数量")
    private int pageSize = 10;
}
