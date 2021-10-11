package com.yh.cloud.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.model.po.*;
import com.yh.cloud.generator.model.vo.PostgreSQLQuery;

import java.util.List;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/6/2
 */
public interface PostgreSQLService {
    IPage<TablePo> getTableList(PostgreSQLQuery<Map<String, Object>> query);

    List<ColumnPo> getTableColumn(String tableName);

    byte[] generatorCode(GeneratorPo generatorPo);
}
