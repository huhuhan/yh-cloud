package com.yh.common.db.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类，以下表中字段有才集成该类
 * @author yanghan
 * @date 2020/12/10
 */
@Setter
@Getter
public class SuperEntity<T extends Model<?>> extends Model<T> {

    @TableId(value = "id_", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "create_time_", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time_", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
