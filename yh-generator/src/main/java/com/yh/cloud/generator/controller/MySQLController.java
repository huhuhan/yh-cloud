package com.yh.cloud.generator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.model.po.ColumnPo;
import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.po.TablePo;
import com.yh.cloud.generator.model.vo.MySQLQuery;
import com.yh.cloud.generator.service.MySQLService;
import com.yh.cloud.generator.utils.GeneratorUtil;
import com.yh.common.web.wrapper.ReturnWrapMapper;
import com.yh.common.web.wrapper.ReturnWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 代码生成器
 *
 * @author yanghan
 * @date 2021/6/2
 */
@Api(tags = "代码生成器-MySQL数据库")
@RequestMapping("/generator/mysql")
@RestController
public class MySQLController {
    @Autowired
    private MySQLService generatorService;

    @ApiOperation("表对象，列表")
    @GetMapping("/list")
    public ReturnWrapper<IPage<TablePo>> getTableList(MySQLQuery<Map<String, Object>> generatorQuery) {
        return ReturnWrapMapper.ok(generatorService.getTableList(generatorQuery));
    }

    @ApiOperation("表字段对象，列表")
    @GetMapping("/column")
    public ReturnWrapper<List<ColumnPo>> getTableColumn(@RequestParam String tableName) {
        return ReturnWrapMapper.ok(generatorService.getTableColumn(tableName));
    }

    @ApiOperation("生成模板代码")
    @GetMapping("/code")
    public void makeCode(GeneratorPo generatorPo, HttpServletResponse response) throws Exception {
        generatorPo = GeneratorUtil.checkGeneratorPo(generatorPo);
        byte[] data = generatorService.generatorCode(generatorPo);
        response.reset();
        String fileName = generatorPo.getModuleValue() + ".zip";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
