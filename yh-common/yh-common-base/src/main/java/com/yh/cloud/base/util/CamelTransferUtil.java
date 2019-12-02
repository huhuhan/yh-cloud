package com.yh.cloud.base.util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 驼峰字符串转化下划线工具类
 * 基于jdk1.8
 *
 * @author yanghan
 * @date 2019/7/18
 */
public class CamelTransferUtil {
    public static final char UNDERLINE = '_';

    /**
     * 驼峰转下划线
     *
     * @param origin
     * @return
     */
    public static String camelToUnderline(String origin) {
        return stringProcess(
                origin, (prev, c) -> {
                    if (Character.isLowerCase(prev) && Character.isUpperCase(c)) {
                        return "" + UNDERLINE + Character.toLowerCase(c);
                    }
                    return "" + c;
                }
        );
    }

    /**
     * 下划线转驼峰
     *
     * @param origin
     * @return
     */
    public static String underlineToCamel(String origin) {
        return stringProcess(
                origin, (prev, c) -> {
                    if (prev == '_' && Character.isLowerCase(c)) {
                        return "" + Character.toUpperCase(c);
                    }
                    if (c == '_') {
                        return "";
                    }
                    return "" + c;
                }
        );
    }

    /**
     * 按字符逐个判断后重新拼接字符串
     *
     * @param origin
     * @param convertFunc
     * @return
     */
    public static String stringProcess(String origin, BiFunction<Character, Character, String> convertFunc) {
        if (origin == null || "".equals(origin.trim())) {
            return "";
        }
        String newOrigin = "0" + origin;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < newOrigin.length() - 1; i++) {
            char prev = newOrigin.charAt(i);
            char c = newOrigin.charAt(i + 1);
            sb.append(convertFunc.apply(prev, c));
        }
        return sb.toString();
    }

    /**
     * 递归转化map里所有key驼峰转下划线
     *
     * @param map
     * @param resultMap
     * @param ignoreKeys 忽略key集合
     */
    public static void transferKeyToUnderline(Map<String, Object> map,
                                              Map<String, Object> resultMap,
                                              Set<String> ignoreKeys) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (ignoreKeys.contains(key)) {
                resultMap.put(key, value);
                continue;
            }
            String newkey = camelToUnderline(key);
            if ((value instanceof List)) {
                List newList = buildValueList(
                        (List) value, ignoreKeys,
                        (m, keys) -> {
                            Map subResultMap = new HashMap();
                            transferKeyToUnderline((Map) m, subResultMap, ignoreKeys);
                            return subResultMap;
                        });
                resultMap.put(newkey, newList);
            } else if (value instanceof Map) {
                Map<String, Object> subResultMap = new HashMap<String, Object>();
                transferKeyToUnderline((Map) value, subResultMap, ignoreKeys);
                resultMap.put(newkey, subResultMap);
            } else {
                resultMap.put(newkey, value);
            }
        }
    }

    /**
     * 递归转化map里所有key驼峰转下划线
     *
     * @param map
     * @param ignoreKeys
     * @return
     */
    public static Map<String, Object> transferKeyToUnderline2(Map<String, Object> map,
                                                              Set<String> ignoreKeys) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (ignoreKeys.contains(key)) {
                resultMap.put(key, value);
                continue;
            }
            String newkey = camelToUnderline(key);
            if ((value instanceof List)) {
                List valList = buildValueList((List) value, ignoreKeys,
                        (m, keys) -> transferKeyToUnderline2(m, keys));
                resultMap.put(newkey, valList);
            } else if (value instanceof Map) {
                Map<String, Object> subResultMap = transferKeyToUnderline2((Map) value, ignoreKeys);
                resultMap.put(newkey, subResultMap);
            } else {
                resultMap.put(newkey, value);
            }
        }
        return resultMap;
    }

    /**
     * 重新构造集合
     * 暂不考虑集合内嵌集合
     *
     * @param valList
     * @param ignoreKeys
     * @param transferFunc camelToUnderline、underlineToCamel
     * @return
     */
    public static List buildValueList(List valList, Set<String> ignoreKeys,
                                      BiFunction<Map, Set, Map> transferFunc) {
        if (valList == null || valList.size() == 0) {
            return valList;
        }
        //判断集合数据类型
        Object first = valList.get(0);
        if (!(first instanceof List) && !(first instanceof Map)) {
            return valList;
        }
        //判断List<Map>
        List newList = new ArrayList();
        for (Object val : valList) {
            Map<String, Object> subResultMap = transferFunc.apply((Map) val, ignoreKeys);
            newList.add(subResultMap);
        }
        return newList;
    }

    /**
     * Map转化通用方法
     *
     * @param map
     * @param keyFunc    camelToUnderline、underlineToCamel、自定义转化方法
     * @param ignoreKeys
     * @return
     */
    public static Map<String, Object> generalMapProcess(Map<String, Object> map,
                                                        Function<String, String> keyFunc,
                                                        Set<String> ignoreKeys) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        map.forEach(
                (key, value) -> {
                    if (ignoreKeys.contains(key)) {
                        resultMap.put(key, value);
                    } else {
                        String newkey = keyFunc.apply(key);
                        if ((value instanceof List)) {
                            resultMap.put(keyFunc.apply(key),
                                    buildValueList((List) value, ignoreKeys,
                                            (m, keys) -> generalMapProcess(m, keyFunc, ignoreKeys)));
                        } else if (value instanceof Map) {
                            Map<String, Object> subResultMap = generalMapProcess((Map) value, keyFunc, ignoreKeys);
                            resultMap.put(newkey, subResultMap);
                        } else {
                            resultMap.put(keyFunc.apply(key), value);
                        }
                    }
                }
        );
        return resultMap;
    }

    /**
     * List转化通用方法
     *
     * @param list
     * @param keyFunc    camelToUnderline、underlineToCamel、自定义转化方法
     * @param ignoreKeys
     * @return
     */
    public static List generalListProcess(List list,
                                          Function<String, String> keyFunc,
                                          Set<String> ignoreKeys) {
        List resultList = new ArrayList();
        if (list == null || list.size() == 0) {
            return list;
        }
        //判断集合类型
        list.forEach(
                value -> {
                    if (ignoreKeys.contains(value)) {
                        resultList.add(value);
                    } else if (value instanceof Map) {
                        Map<String, Object> subResultMap = generalMapProcess((Map) value, keyFunc, ignoreKeys);
                        resultList.add(subResultMap);
                    } else {
                        String keyValue = keyFunc.apply((String) value);
                        resultList.add(value);
                    }
                });
        return resultList;
    }
}
