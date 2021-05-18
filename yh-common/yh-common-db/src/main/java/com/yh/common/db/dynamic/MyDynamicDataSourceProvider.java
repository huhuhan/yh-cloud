package com.yh.common.db.dynamic;

import java.util.Map;

/**
 * 数据库源，加载接口
 *
 * @author yanghan
 * @date 2021/2/4
 */
public interface MyDynamicDataSourceProvider {
    Map<Object, Object> loadDataSources();
}
