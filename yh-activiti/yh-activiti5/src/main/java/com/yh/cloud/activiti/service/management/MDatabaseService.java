package com.yh.cloud.activiti.service.management;

import com.yh.cloud.web.wrapper.ReturnMsg;
import org.activiti.engine.ManagementService;
import org.activiti.engine.management.TableMetaData;
import org.activiti.engine.management.TablePage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Activiti数据库管理
 *
 * @author yanghan
 * @date 2019/10/31
 */
@Service
public class MDatabaseService {

    @Autowired
    ManagementService managementService;

    public Object getDatabaseList(String tableName, Integer pageSize, Integer pageNum) {
        // 表名、数据量
        Map<String, Long> tableCount = managementService.getTableCount();
        List<String> keys = new ArrayList<String>();
        keys.addAll(tableCount.keySet());
        Collections.sort(keys);
        TreeMap<String, Long> sortedTableCount = new TreeMap<String, Long>();
        for (String key : keys) {
            sortedTableCount.put(key, tableCount.get(key));
        }

        ReturnMsg returnMsg = new ReturnMsg<>()
                .keyValue("tables", sortedTableCount);

        // 读取表记录
        if (StringUtils.isNotBlank(tableName)) {
            TableMetaData tableMetaData = managementService.getTableMetaData(tableName);
            TablePage tablePages = managementService.createTablePageQuery().tableName(tableName).listPage(pageNum, pageSize);
            returnMsg.keyValue("current_table", tableMetaData)
                    .keyValue("current_table_data", tablePages);
        }
        return returnMsg;

    }
}
