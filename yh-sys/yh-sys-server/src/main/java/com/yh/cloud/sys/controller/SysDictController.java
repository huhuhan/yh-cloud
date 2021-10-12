package com.yh.cloud.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.sys.model.entity.SysDict;
import com.yh.cloud.sys.model.vo.SysDictQuery;
import com.yh.cloud.sys.service.ISysDictService;
import com.yh.common.web.util.AdminUtil;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据字典
 *
 * @author yanghan
 * @date 2021-09-24 16:55:24
 */
@RestController
@RequestMapping("/sysdict")
@Api(tags = "数据字典")
public class SysDictController {
    @Autowired
    private ISysDictService sysDictService;

    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public ReturnWrapper<IPage<SysDict>> findList(SysDictQuery<SysDict> sysDictQuery) {
        return ReturnWrapMapper.ok(sysDictService.findList(sysDictQuery));
    }

    @ApiOperation(value = "查询对象")
    @GetMapping("/{id}")
    public ReturnWrapper<SysDict> findById(@PathVariable Long id) {
        return ReturnWrapMapper.ok(sysDictService.getById(id));
    }

    @ApiOperation(value = "保存")
    @PostMapping("/save")
    public ReturnWrapper save(@Valid @RequestBody SysDict sysDict) {
        return ReturnWrapMapper.ok(sysDictService.save(sysDict));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public ReturnWrapper delete(@PathVariable Long id) {
        AdminUtil.unAuthorized();
        return ReturnWrapMapper.ok(sysDictService.removeById(id));
    }

    @ApiOperation(value = "根据字典类型获取")
    @GetMapping("/key")
    public ReturnWrapper<List<SysDict>> findByKey(@RequestParam String key) {
        return ReturnWrapMapper.ok(sysDictService.findByKey(key));
    }
}
