package com.yh.cloud.activiti.leave.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author yanghan
 * @date 2019/10/28
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final String CREATE_DATE = "createDate";

    private final String UPDATE_DATE = "updateDate";


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("mybatis.plus 新增记录时自动填充属性");
//        setFieldValByName(CREATE_DATE, LocalDateTime.now(), metaObject);
        setFieldValByName(CREATE_DATE, LocalDateTime.now(), metaObject);
        setFieldValByName(UPDATE_DATE, LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("mybatis.plus 更新记录时自动填充属性");
        setFieldValByName(UPDATE_DATE, LocalDateTime.now(), metaObject);
    }


}
