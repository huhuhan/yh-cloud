package com.yh.cloud.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.cloud.generator.model.po.ColumnPo;
import com.yh.cloud.generator.model.po.TablePo;
import com.yh.cloud.generator.model.vo.MySQLQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yanghan
 * @date 2021/6/2
 */
public interface MySQLMapper {
    IPage<TablePo> getTableList(IPage page, @Param("p") MySQLQuery generatorQuery);

    List<ColumnPo> getTableColumn(String tableName);
}
