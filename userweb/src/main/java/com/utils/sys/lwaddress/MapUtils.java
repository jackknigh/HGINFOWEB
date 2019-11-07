/**
 * Copyright (C) 2016-2017 Hangzhou HSH Co. Ltd.
 * All right reserved.
 *
 * @author: Simon.lee
 * date: 2017-03-22 11:22
 */
package com.utils.sys.lwaddress;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map 工具类
 *
 * @author simon
 */
public class MapUtils {
    /**
     * 从 Map 集合中返回属性值
     *
     * @param map
     * @param key
     * @param defaultValue
     * @param <E>
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final static <E> E get(Map map, Object key, E defaultValue) {
        Object o = map.get(key);
        if (o == null) {
            return defaultValue;
        }
        return (E) o;
    }

    /**
     * Map对象转化成 JavaBean对象
     *
     * @param obj
     * @param map
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked", "hiding"})
    public static void map2Java(Object obj, Map<String, Object> map) throws Exception {
        try {
            //创建 Bean 属性
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                for (PropertyDescriptor pd : propertyDescriptors) {
                    //属性名 小写
                    String propertyName = pd.getName().toLowerCase();
                    // 属性值 map也要小写。。。
                    if (map.containsKey(propertyName)) {

                        Object propertyValue = map.get(propertyName);

                        if (propertyValue != null) {
                            pd.getWriteMethod().invoke(obj, propertyValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * JavaBean对象转化成Map对象
     *
     * @param javaBean
     * @param needLowCase
     * @return
     * @author jqlin
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map java2Map(Object javaBean, boolean needLowCase) throws Exception {
        Map map = new HashMap(16);

        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                // javaBean属性名
                String propertyName = null;
                // javaBean属性值
                Object propertyValue = null;
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = needLowCase ? pd.getName().toLowerCase() : pd.getName();
                    if (!"class".equals(propertyName)) {
                        Method readMethod = pd.getReadMethod();
                        propertyValue = readMethod.invoke(javaBean);
                        map.put(propertyName, propertyValue);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return map;
    }

    /**
     * JavaBean对象转化成Map对象(非空数据)
     *
     * @param javaBean
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> java2MapNotNull(Object javaBean) {
        Map map = new HashMap(16);

        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                // javaBean属性名
                String propertyName = null;
                // javaBean属性值
                Object propertyValue = null;
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (!"class".equals(propertyName)) {
                        Method readMethod = pd.getReadMethod();
                        propertyValue = readMethod.invoke(javaBean);
                        if (propertyValue == null || "".equals(propertyValue)) {
                            continue;
                        }
                        map.put(propertyName, propertyValue);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 将 List<Map>对象转化为List<JavaBean>
     *
     * @param listMap listMap listMap 数据
     * @param T       Bean 类型
     * @param <T>
     * @return list<JavaBean> 对象
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> convertListMap2ListBean(List<Map<String, Object>> listMap, Class T) throws Exception {
        if (listMap == null) {
            return new ArrayList<>(0);
        }
        int listSize = listMap.size();
        List<T> beanList = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            Map<String, Object> map = listMap.get(i);
            T bean = (T) T.newInstance();
            map2Java(bean, map);
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 将 List<JavaBean>对象转化为 List<Map>
     *
     * @param listBean 数据
     * @param T        Bean 类型
     * @param <T>
     * @return List<Map < String, Object>> 对象
     * @throws Exception
     */
    public static <T> List<Map<String, Object>> convertListBean2ListMap(List<T> listBean, Class<T> T) throws Exception {
        if (listBean == null) {
            return new ArrayList<>(0);
        }
        int listSize = listBean.size();
        List<Map<String, Object>> mapList = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            Object bean = listBean.get(i);
            Map map = java2Map(bean, false);
            mapList.add(map);
        }
        return mapList;
    }

    private static Object getPropertyValueByName(Object object, String name) {
        try {
            String firstLetter = name.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + name.substring(1);
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            return value;
        } catch (Exception var6) {
            System.out.println("getPropertyValueByName " + var6.getMessage());
            return null;
        }
    }

    public static Map<String, String> makeMapByObject(Object object) {
        Map<String, String> queryParams = null;
        if (null != object) {
            queryParams = new HashMap();
            String[] propertyNames = getPropertyNames(object);
            String[] var3 = propertyNames;
            int var4 = propertyNames.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String string = var3[var5];
                Object value = getPropertyValueByName(object, string);
                if (null != value) {
                    queryParams.put(string, value.toString());
                }
            }
        }

        return queryParams;
    }

    private static String[] getPropertyNames(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String[] properties = new String[fields.length];

        for (int i = 0; i < fields.length; ++i) {
            properties[i] = fields[i].getName();
        }

        return properties;
    }
}
