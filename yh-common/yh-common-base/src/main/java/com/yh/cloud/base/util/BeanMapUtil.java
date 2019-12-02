package com.yh.cloud.base.util;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanghan
 * @date 2019/6/28
 */
@Slf4j
public class BeanMapUtil {
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 转换bean为map
     *
     * @param source 要转换的bean
     * @param <T>    bean类型
     * @return 转换结果
     */
    public static <T> Map<String, Object> beanToMap(T source) throws IllegalAccessException {
        Map<String, Object> result = new HashMap<>();

        Class<?> sourceClass = source.getClass();
        //拿到所有的字段,不包括继承的字段
        Field[] sourceFiled = sourceClass.getDeclaredFields();
        for (Field field : sourceFiled) {
            if (!SERIAL_VERSION_UID.equalsIgnoreCase(field.getName())) {
                //设置可访问,不然拿不到private
                field.setAccessible(true);
                //配置了注解的话则使用注解名称,作为header字段
                FieldName fieldName = field.getAnnotation(FieldName.class);
                if (fieldName == null) {
                    result.put(field.getName(), field.get(source));
                } else {
                    if (fieldName.Ignore()) {
                        continue;
                    }
                    result.put(fieldName.value(), field.get(source));
                }
            }
        }
        return result;
    }

    /**
     * map转bean
     *
     * @param source   map属性
     * @param instance 要转换成的备案
     * @return 该bean
     */
    public static <T> T mapToBean(Map<String, Object> source, Class<T> instance) {
        try {
            T object = instance.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!SERIAL_VERSION_UID.equalsIgnoreCase(field.getName())) {
                    FieldName fieldName = field.getAnnotation(FieldName.class);
                    if (fieldName != null) {
                        //todo:set方法，类型强制会引发问题
                        field.set(object, source.get(fieldName.value()));
                    } else {

                    }
                }
            }
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 自定义字段名
     *
     * @author yanghan
     * @date 2019/7/3
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface FieldName {
        /**
         * 字段名
         */
        String value() default "";

        /**
         * 是否忽略
         */
        boolean Ignore() default false;
    }


    /**
     * 使用Introspector，map集合成javabean
     *
     * @param map       map
     * @param beanClass bean的Class类
     * @return bean对象
     */
    public static <T> T mapToBean2(Map<String, Object> map, Class<T> beanClass) {

        if (null == map || map.isEmpty()) {
            return null;
        }

        try {
            T t = beanClass.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(t, map.get(property.getName()));
                }
            }
            return t;
        } catch (Exception ex) {
            log.error("########map集合转javabean出错######，错误信息，{}", ex.getMessage());
            throw new RuntimeException();
        }

    }

    /**
     * 使用Introspector，对象转换为map集合
     *
     * @param beanObj javabean对象
     * @return map集合
     */
    public static Map<String, Object> beanToMap2(Object beanObj) {

        if (null == beanObj) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(beanObj) : null;
                map.put(key, value);
            }

            return map;
        } catch (Exception ex) {
            log.error("########javabean集合转map出错######，错误信息，{}", ex.getMessage());
            throw new RuntimeException();
        }
    }

}

