package com.utils.sys;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>方法 BeanUtil : <p>
 * <p>说明:通用的工具类</p>
 * <pre>
 * @author JackZhou
 * @date 2017/3/11 21:53
 * </pre>
 */
public class BeanUtil {

    public static Map<String, Object> obj2Map(Object obj){
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T> T obj2Class(Object o,Class<T> clz) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, clz);
    }
}
