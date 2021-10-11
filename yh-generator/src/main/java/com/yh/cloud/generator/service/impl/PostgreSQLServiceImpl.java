package com.yh.cloud.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.mapper.PostgreSQLMapper;
import com.yh.cloud.generator.model.emun.GeneratorConstant;
import com.yh.cloud.generator.model.po.ColumnPo;
import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.po.TablePo;
import com.yh.cloud.generator.model.vo.PostgreSQLQuery;
import com.yh.cloud.generator.service.PostgreSQLService;
import com.yh.cloud.generator.utils.GeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
@SuppressWarnings("all")
public class PostgreSQLServiceImpl implements PostgreSQLService {
    private final PostgreSQLMapper generatorMapper;

    @Override
    public IPage<TablePo> getTableList(PostgreSQLQuery<Map<String, Object>> query) {
        IPage pageQuery = query.initPageQuery();
        return generatorMapper.getTableList(pageQuery, query);
    }

    @Override
    public List<ColumnPo> getTableColumn(String tableName) {
        return generatorMapper.getTableColumn(tableName);
    }

    /**
     * 获取表对象
     *
     * @param tableName
     * @return
     */
    private TablePo getTable(String tableName) {
        PostgreSQLQuery<Map<String, Object>> query = new PostgreSQLQuery<>();
        query.setTableName(tableName);
        IPage<TablePo> page = this.getTableList(query);

        if (CollectionUtil.isNotEmpty(page.getRecords())) {
            Object table = page.getRecords().get(0);
            return BeanUtil.toBean(table, TablePo.class);
        }
        return null;
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
                TablePo table = this.getTable(tableName);
                //查询列信息
                List<ColumnPo> columns = this.getTableColumn(tableName);
                //生成代码
                GeneratorUtil.generatorCode(generatorPo, table, columns, zip);
            }
        } catch (IOException e) {
            Console.error("generatorCode-error: ", e);
        }
        return outputStream.toByteArray();
    }


}
