package com.yh.cloud.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.model.po.ColumnPo;
import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.po.TablePo;
import com.yh.cloud.generator.model.vo.MySQLQuery;

import java.util.List;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/6/2
 */
public interface MySQLService {
    IPage<TablePo> getTableList(MySQLQuery<Map<String, Object>> generatorQuery);

    List<ColumnPo> getTableColumn(String tableName);

    byte[] generatorCode(GeneratorPo generatorPo);
}
