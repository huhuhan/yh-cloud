package com.yh.cloud.generator.controller;

import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.vo.GeneratorQuery;
import com.yh.cloud.generator.service.GeneratorService;
import com.yh.cloud.generator.utils.GenUtils;
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
import java.util.Map;


/**
 * 代码生成器
 *
 * @author yanghan
 * @date 2021/6/2
 */

@Api(tags = "代码生成器")
@RequestMapping("/generator")
@RestController
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;

    @ApiOperation("MYSQL数据库，表对象，列表")
    @GetMapping("/mysql/list")
    public ReturnWrapper getTableList(GeneratorQuery<Map<String, Object>> generatorQuery) {
        return ReturnWrapMapper.ok(generatorService.queryList(generatorQuery));
    }

    @ApiOperation("MYSQL数据库，表对象")
    @GetMapping("/mysql/table")
    public ReturnWrapper getMySqlTable(@RequestParam String tableName) {
        return ReturnWrapMapper.ok(generatorService.getMySqlTable(tableName));
    }

    @ApiOperation("MYSQL数据库，表字段对象，列表")
    @GetMapping("/mysql/column")
    public ReturnWrapper getMySqlColumnList(@RequestParam String tableName) {
        return ReturnWrapMapper.ok(generatorService.getMySqlColumnList(tableName));
    }

    @ApiOperation("MYSQL数据库，生成模板代码")
    @GetMapping("/mysql/code")
    public void makeCode(GeneratorPo generatorPo, HttpServletResponse response) throws Exception {
        generatorPo = GenUtils.checkGeneratorPo(generatorPo);
        byte[] data = generatorService.generatorCode(generatorPo);
        response.reset();
        String fileName = generatorPo.getModuleValue() + ".zip";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
