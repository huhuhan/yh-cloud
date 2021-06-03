package com.yh.cloud.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.po.MySqlColumnPo;
import com.yh.cloud.generator.model.po.MySqlTablePo;
import com.yh.cloud.generator.model.vo.GeneratorQuery;

import java.util.List;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/6/2
 */
public interface GeneratorService {
    IPage queryList(GeneratorQuery<Map<String, Object>> generatorQuery);

    MySqlTablePo getMySqlTable(String tableName);

    List<MySqlColumnPo> getMySqlColumnList(String tableName);

    byte[] generatorCode(GeneratorPo generatorPo);
}
