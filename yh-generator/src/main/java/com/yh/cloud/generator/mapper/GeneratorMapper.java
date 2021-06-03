package com.yh.cloud.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.cloud.generator.model.po.MySqlColumnPo;
import com.yh.cloud.generator.model.po.MySqlTablePo;
import com.yh.cloud.generator.model.vo.GeneratorQuery;
import com.yh.common.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yanghan
 * @date 2021/6/2
 */
public interface GeneratorMapper {
    IPage<Map<String, Object>> queryList(IPage page, @Param("p") GeneratorQuery generatorQuery);

    int queryTotal(Map<String, Object> map);

    MySqlTablePo getMySqlTable(String tableName);

    List<MySqlColumnPo> getMySqlColumnList(String tableName);
}
