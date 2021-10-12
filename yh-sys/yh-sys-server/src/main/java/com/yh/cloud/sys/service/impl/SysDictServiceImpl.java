package com.yh.cloud.sys.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.cloud.sys.mapper.SysDictMapper;
import com.yh.cloud.sys.model.entity.SysDict;
import com.yh.cloud.sys.model.vo.SysDictQuery;
import com.yh.cloud.sys.service.ISysDictService;
import com.yh.common.db.service.impl.SuperServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典
 *
 * @author yanghan
 * @date 2021-09-24 16:55:24
 */
@Service
public class SysDictServiceImpl extends SuperServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public IPage<SysDict> findList(SysDictQuery<SysDict> sysDictQuery) {
        IPage<SysDict> pageQuery = sysDictQuery.initPageQuery();
        QueryWrapper<SysDict> queryWrapper = Wrappers.query();
        sysDictQuery.initFilter(queryWrapper);
        return this.page(pageQuery, queryWrapper);
    }

    @Override
    public List<SysDict> findByKey(String key) {
        return this.list(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getKey, key)
                .eq(SysDict::getIsRoot, Boolean.FALSE)
        );
    }
}
