package com.yh.cloud.generator.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配置信息
 *
 * @author yanghan
 * @date 2021/6/3
 */
@Data
public class GeneratorPo {

    @ApiModelProperty("相对包名，比如com.yh")
    private String packageValue;

    @ApiModelProperty("模块名，比如user")
    private String moduleValue;

    @ApiModelProperty("作者，比如yh")
    private String authorValue;

    @ApiModelProperty("邮箱，比如\"yhgogo816@gmail.com\"")
    private String emailValue;

    @ApiModelProperty("表名前缀，比如yh_")
    private String tablePrefixValue;

    @ApiModelProperty("是否继承基础父类，参考SuperEntity、SuperMapper、SupperService")
    private Boolean hasSuperObj;

    @ApiModelProperty("待生成的表名，多个用|区分")
    private String toTables;


}
