package com.yh.cloud.web.wrapper;

import java.util.HashMap;

/**
 * @author yanghan
 * @date 2019/10/30
 */
public class ReturnMsg<K,V> extends HashMap<K,V> {

    public ReturnMsg keyValue(K key, V value) {
        super.put(key, value);
        return this;
    }
}
