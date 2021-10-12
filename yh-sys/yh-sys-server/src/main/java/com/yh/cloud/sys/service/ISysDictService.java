package com.yh.cloud.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.sys.model.entity.SysDict;
import com.yh.cloud.sys.model.vo.SysDictQuery;
import com.yh.common.db.service.ISuperService;

import java.util.List;

/**
 * 数据字典
 *
 * @author yanghan
 * @date 2021-09-24 16:55:24
 */
public interface ISysDictService extends ISuperService<SysDict> {
    /**
     * 列表
     * @author yanghan
     * @param sysDictQuery
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @date 2021-09-24 16:55:24
     */
    IPage<SysDict> findList(SysDictQuery<SysDict> sysDictQuery);

    /**
     * 根据字典类型获取
     * @author yanghan
     * @param key
     * @return java.util.List<com.yh.cloud.sys.model.entity.SysDict>
     * @date 2021/10/12
     */
    List<SysDict> findByKey(String key);
}

