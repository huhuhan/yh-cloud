package com.yh.cloud.generator.service.impl;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.mapper.GeneratorMapper;
import com.yh.cloud.generator.model.emun.GeneratorConstant;
import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.po.MySqlColumnPo;
import com.yh.cloud.generator.model.po.MySqlTablePo;
import com.yh.cloud.generator.model.vo.GeneratorQuery;
import com.yh.cloud.generator.service.GeneratorService;
import com.yh.cloud.generator.utils.GenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;


/**
 * @author yanghan
 * @date 2021/6/2
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {
    @Resource
    private GeneratorMapper generatorMapper;

    @Override
    public IPage queryList(GeneratorQuery<Map<String, Object>> generatorQuery) {
        IPage pageQuery = generatorQuery.initPageQuery();
        return generatorMapper.queryList(pageQuery, generatorQuery);
    }

    @Override
    public List<MySqlColumnPo> getMySqlColumnList(String tableName) {
        return generatorMapper.getMySqlColumnList(tableName);
    }

    @Override
    public MySqlTablePo getMySqlTable(String tableName) {
        return generatorMapper.getMySqlTable(tableName);
    }

    @Override
    public byte[] generatorCode(GeneratorPo generatorPo) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (
                ZipOutputStream zip = new ZipOutputStream(outputStream)
        ) {

            String[] tableNames = generatorPo.getToTables().split(GeneratorConstant.TO_TABLES_SPLIT);

            for (String tableName : tableNames) {
                //查询表信息
                MySqlTablePo table = this.getMySqlTable(tableName);
                //查询列信息
                List<MySqlColumnPo> columns = this.getMySqlColumnList(tableName);
                //生成代码
                GenUtils.generatorCode(generatorPo, table, columns, zip);
            }
        } catch (IOException e) {
            Console.error("generatorCode-error: ", e);
        }
        return outputStream.toByteArray();
    }


}
