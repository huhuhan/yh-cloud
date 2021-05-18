package com.yh.common.db.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yh.cloud.base.support.IdGenerator;
import com.yh.common.db.properties.MybatisPlusAutoFillProperties;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;


/**
 * 自动填充字段
 * 参考文档：https://mybatis.plus/guide/auto-fill-metainfo.html
 *
 * @author yanghan
 * @date 2020/12/3
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    private MybatisPlusAutoFillProperties autoFillProperties;

    public MyMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
        this.autoFillProperties = autoFillProperties;
    }

    /**
     * 是否开启了插入填充
     */
    @Override
    public boolean openInsertFill() {
        return autoFillProperties.getEnableInsertFill();
    }

    /**
     * 是否开启了更新填充
     */
    @Override
    public boolean openUpdateFill() {
        return autoFillProperties.getEnableUpdateFill();
    }

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充主键ID
        Object id = getFieldValByName(autoFillProperties.getIdField(), metaObject);
        if (null == id) {
            setFieldValByName(autoFillProperties.getCreateTimeField(), IdGenerator.nextStringId(), metaObject);
        }

        // 填充时间字段
        Object createTime = getFieldValByName(autoFillProperties.getCreateTimeField(), metaObject);
        Object updateTime = getFieldValByName(autoFillProperties.getUpdateTimeField(), metaObject);
        Date date = new Date();
        if (null == createTime) {
            setFieldValByName(autoFillProperties.getCreateTimeField(), date, metaObject);
        }
        if (null == updateTime) {
            setFieldValByName(autoFillProperties.getUpdateTimeField(), date, metaObject);
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(autoFillProperties.getUpdateTimeField(), new Date(), metaObject);
    }
}