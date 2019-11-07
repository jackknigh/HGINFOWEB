package com.utils.sys.lwaddress;


import java.io.*;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object 工具处理类
 * 具体实现参考网络
 *
 * @author simon
 */
public class ObjectUtil {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * Bean 对象之间赋值
     *
     * @param fromBean
     * @param destBean
     */
    public static void Bean2Bean(Object fromBean, Object destBean) {
        try {

            Map map = MapUtils.java2Map(fromBean, true);
            MapUtils.map2Java(destBean, map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 根据属性名获取getter setter方法名
     *
     * @param field  字段名
     * @param prefix 前缀
     * @return
     */
    public static String methodName(String field, String prefix) {

        if (field == null) {
            return null;
        }
        if (field.length() > 1 && Character.isUpperCase(field.charAt(1))) {
            return prefix + field;
        } else {
            return prefix + field.substring(0, 1).toUpperCase() + field.substring(1);
        }
    }

    /**
     * 根据属性名获取值
     *
     * @param obj   对象
     * @param field 字段名
     * @return
     */
    public static Object getValueByKey(Object obj, String field) {

        try {
            Method method = null;
            if (obj == null || StrUtil.isBlank(field)) {
                return null;
            }
            String[] fieldArr = field.split("[.]");
            for (String str : fieldArr) {
                method = obj.getClass().getMethod(methodName(str, "get"));
                obj = method.invoke(obj);
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将对象object特定方法的返回值（
     *
     * @param obj    对象
     * @param field  方法
     * @param format 格式
     * @return
     */
    public static String ObjectToString(Object obj, String field, String format) {

        try {
            Method method = null;
            if (obj == null || StrUtil.isBlank(field)) {
                return null;
            }
            String[] fieldArr = field.split("[.]");
            for (String str : fieldArr) {
                if (method != null) {
                    obj = method.invoke(obj);
                }
                method = obj.getClass().getMethod(methodName(str, "get"));
            }
            return ObjectToString(obj, method, format);

        } catch (Exception e) {
            return null;
        }

    }


    /*
     * public static void main(String[] args) { JIANG jiang = new JIANG(); try {
     * Object object = ObjectSetValue(jiang, "name", "test");
     * System.out.println(jiang.getPolitic()); } catch (Exception e) {
     * Auto-generated catch block e.printStackTrace(); }
     *
     * }
     */

    /**
     * 将对象object特定方法的返回值（主要是get方法）按照format格式转化为字符串类型
     *
     * @param object 对象
     * @param method 方法
     * @param format 格式
     * @return
     */
    public static String ObjectToString(Object object, Method method, String format) throws Exception {

        if (object == null || method == null) {
            return null;
        }
        // 时间类型
        if (method.getReturnType().getName().equals(Date.class.getName())) {
            if (StrUtil.isEmpty(format)) {
                return DateUtil.format((Date) method.invoke(object));
            } else {
                return DateUtil.format((Date) method.invoke(object), format);
            }
        }

        return method.invoke(object).toString();

    }


    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * List 深拷贝 使用序列化方法
     *
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    /**
     * Determine whether the given array is empty:
     * i.e. {@code null} or of zero length.
     *
     * @param array the array to check
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }
}
