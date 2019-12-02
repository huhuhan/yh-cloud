package com.yh.cloud.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * jackson 工具类
 *
 * @author yanghan
 * @date 2019/7/31
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //实体类中没有的属性不抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转json
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        try {
            String string = objectMapper.writeValueAsString(object);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json转对象
     *
     * @param json
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String json, Class<T> classType) {
        try {
            T t = objectMapper.readValue(json, classType);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转集合
     *
     * @param json
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> classType) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, classType);
        try {
            List<T> list = objectMapper.readValue(json, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
