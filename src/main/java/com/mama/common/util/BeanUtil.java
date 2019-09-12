package com.mama.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @Auther: 杨浩宇
 * @Date: 2018/7/18 17:22
 * @Description: bean 工具类
 */
public class BeanUtil {

    private static WriteLock lock = new ReentrantReadWriteLock().writeLock();

    private static final Map<Class<?>, Map<String, Field>> METADATA = new HashMap<>();

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 对象转换
     *
     * @param source      源对象
     * @param targetClass 目标类
     * @return 返回一个新的目标类对象, 该目标类对象的属性值从源对象中拷贝而来
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return dozer.map(source, targetClass);
    }

    /**
     * 对象列表转换
     *
     * @param sourceList  源对象列表
     * @param targetClass 目标类
     * @return 返回一个新的目标对象列表, 每个目标类对象的属性值从源对象中拷贝而来
     */
    public static <T> List<T> convert(Collection<?> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.size() == 0) {
            return null;
        }
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            if (sourceObject != null) {
                T destinationObject = dozer.map(sourceObject, targetClass);
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }

    /**
     * 属性拷贝, 当且仅当两个对象的非静态属性名称相同且对应的属性类型也相同时才进行属性值拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        dozer.map(source, target);
    }


    /**
     * 创建类的一个实例
     *
     * @param beanClass 类
     */
    public static <T> T newInstance(Class<T> beanClass) {
        try {
            return beanClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置对象属性的值
     *
     * @param bean          目标对象
     * @param field         属性名称
     * @param propertyValue 属性的值
     */
    public static void setPropertyValue(Object bean, Field field, Object propertyValue) {
        try {
            field.set(bean, propertyValue);
        } catch (Throwable e) {
            throw new RuntimeException(bean.getClass().getName() + " " + field.getName() + " " + propertyValue, e);
        }
    }

    /**
     * 获取目标属性的值
     *
     * @param bean  目标对象
     * @param field 属性名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object bean, Field field) {
        try {
            return (T) field.get(bean);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置属性的值
     *
     * @param bean          对象或类
     * @param property      类属性或对象属性名称
     * @param propertyValue 属性的值
     */
    public static void setPropertyValue(Object bean, String property, Object propertyValue) {
        if (bean != null) {
            Class<?> beanClass = null;
            if (bean instanceof Class) {
                beanClass = (Class<?>) bean;
            } else {
                beanClass = bean.getClass();
            }
            Field field = getFieldsMap(beanClass, FieldType.ALL).get(property);
            if (field != null) {
                setPropertyValue(bean, field, propertyValue);
            }
        }
    }

    /**
     * 获取属性的值
     *
     * @param bean     对象或类
     * @param property 类属性名称或对象属性名称
     * @return
     */
    public static <T> T getPropertyValue(Object bean, String property) {
        if (bean != null) {
            Class<?> beanClass = null;
            if (bean instanceof Class) {
                beanClass = (Class<?>) bean;
            } else {
                beanClass = bean.getClass();
            }
            Field field = getFieldsMap(beanClass, FieldType.ALL).get(property);
            if (field != null) {
                return getPropertyValue(bean, field);
            }
        }
        return null;
    }

    /**
     * 获取属性的类型
     *
     * @param bean     对象或类
     * @param property 类属性名称或对象属性名称
     * @return
     */
    public static Class<?> getPropertyType(Object bean, String property) {
        if (bean != null) {
            Class<?> beanClass = null;
            if (bean instanceof Class) {
                beanClass = (Class<?>) bean;
            } else {
                beanClass = bean.getClass();
            }
            Field field = getFieldsMap(beanClass, FieldType.ALL).get(property);
            if (field != null) {
                return field.getType();
            }
        }
        return null;
    }

    /**
     * 将对象转换为散列表
     *
     * @param source 源对象
     * @return
     */
    public static Map<String, Object> convertMap(Object source) {
        return convertMap(source, true);
    }

    /**
     * 将对象转换为散列表
     *
     * @param source              源对象
     * @param convertNullProperty 空属性是否转换
     * @return
     */
    public static Map<String, Object> convertMap(Object source, boolean convertNullProperty) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (source != null) {
            Map<String, Field> sourceFields = getFieldsMap(source.getClass(), FieldType.NOT_STATIC);
            for (String name : sourceFields.keySet()) {
                Object value = getPropertyValue(source, sourceFields.get(name));
                if (value == null && !convertNullProperty) {
                    continue;
                } else {
                    map.put(name, value);
                }
            }
        }
        return map;
    }

    /**
     * 将list<object>转换为 List<Map<String, Object>>
     *
     * @param sources
     * @param convertNullProperty
     * @return
     */
    public static List<Map<String, Object>> convertListMap(List<?> sources, boolean convertNullProperty) {
        List<Map<String, Object>> maps = new ArrayList<>();
        if (sources != null && sources.size() > 0) {
            for (Object object : sources) {
                maps.add(convertMap(object, convertNullProperty));
            }
        }
        return maps;
    }

    /**
     * 将对象转换为Map，并以指定的字段为key
     *
     * @param source  源对象
     * @param keyName 为key的字段名称
     * @return
     */
    public static <T> Map<Object, T> convertMapByFieldNew(List<T> source, String keyName) {
        Map<Object, T> map = new HashMap();
        if (source != null && source.size() > 0) {
            for (T t : source) {
                try {
                    Field field = t.getClass().getDeclaredField(keyName);
                    field.setAccessible(true);
                    Object value = getPropertyValue(t, field);
                    map.put(value, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 将对象转换为Map，并以指定的字段为key
     *
     * @param source  源对象
     * @param keyName 为key的字段名称
     * @return
     */
    public static <A, T> Map<A, T> convertMapByField(List<T> source, String keyName) {
        Map<A, T> map = new HashMap();
        if (source != null && source.size() > 0) {
            for (T t : source) {
                try {
                    Field field = t.getClass().getDeclaredField(keyName);
                    field.setAccessible(true);
                    A value = getPropertyValue(t, field);
                    map.put(value, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 将对象转换为Map，并以指定的字段为key,以指定字段为value
     *
     * @param source    源对象
     * @param keyName   为key的字段名称
     * @param valueName 为value的字段名称
     * @return
     */
    public static <A, T> Map<A, T> convertMapByField(List<?> source, String keyName, String valueName) {
        Map<A, T> map = new HashMap();
        if (source != null && source.size() > 0) {
            for (Object o : source) {
                try {
                    Field keyField = o.getClass().getDeclaredField(keyName);
                    keyField.setAccessible(true);
                    A key = getPropertyValue(o, keyField);
                    Field valueField = o.getClass().getDeclaredField(valueName);
                    valueField.setAccessible(true);
                    T value = getPropertyValue(o, valueField);
                    map.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 将对象转换为Map，并以指定的字段为key，如果key相同则加入value集合中
     *
     * @param source    源对象
     * @param fieldName 为key的字段名称
     * @return
     */
    public static <A, T> Map<A, List<T>> convertMapAndListByField(List<T> source, String fieldName) {
        Map<A, List<T>> map = new HashMap();
        if (source != null && source.size() > 0) {
            for (T t : source) {
                try {
                    Field field = t.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    A value = getPropertyValue(t, field);
                    List<T> tList = map.get(value);
                    if (CollectionUtils.isEmpty(tList)) {
                        tList = new LinkedList<>();
                    }
                    tList.add(t);
                    map.put(value, tList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }


    /**
     * 获取声明的属性表
     *
     * @param beanClass 目标类
     * @param fieldType 属性类型
     * @return
     */
    private static Map<String, Field> getFieldsMap(Class<?> beanClass, FieldType fieldType) {
        Map<String, Field> map = getReferableFieldsMap(beanClass);
        Map<String, Field> target = new HashMap<>();
        if (map != null && !map.isEmpty()) {
            for (String name : map.keySet()) {
                Field field = map.get(name);
                switch (fieldType) {
                    case ALL:
                        target.put(name, field);
                        break;
                    case STATIC:
                        if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
                            target.put(name, field);
                        }
                        break;
                    case NOT_STATIC:
                        if ((field.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                            target.put(name, field);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return target;
    }

    /**
     * 获取当前类声明的属性表
     *
     * @param beanClass 目标类
     * @return
     */
    private static Map<String, Field> getDeclaredFieldsMap(Class<?> beanClass) {
        Field[] fields = beanClass.getDeclaredFields();
        Map<String, Field> map = new HashMap<String, Field>();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field);
            }
        }
        return map;
    }

    /**
     * 获取声明的所有属性表
     *
     * @param beanClass 目标类
     * @return
     */
    private static Map<String, Field> getReferableFieldsMap(Class<?> beanClass) {
        if (!METADATA.containsKey(beanClass)) {
            try {
                lock.lock();
                if (!METADATA.containsKey(beanClass)) {
                    Map<String, Field> map = new HashMap<>();
                    while (beanClass != null) {
                        map.putAll(getDeclaredFieldsMap(beanClass));
                        beanClass = beanClass.getSuperclass();
                    }
                    METADATA.put(beanClass, map);
                }
            } finally {
                lock.unlock();
            }
        }
        return METADATA.get(beanClass);
    }

    enum FieldType {STATIC, NOT_STATIC, ALL}

    /**
     * 调用成员方法(或类方法), 该方法要求被调方法参数必须不能有基本数据类型
     *
     * @param object     具体的对象(或类)
     * @param methodName 对象的成员方法名称(或类的类方法名称)
     * @param argValues  方法的参数值列表
     * @return
     */
    public static <E> E invokeMethod(Object object, String methodName, Object... argValues) {
        Class<?>[] classes = null;
        if (argValues != null && argValues.length > 0) {
            int length = argValues.length;
            classes = new Class<?>[length];
            for (int i = 0; i < length; i++) {
                classes[i] = argValues[i].getClass();
            }
        }
        return invokeMethod(object, methodName, argValues, classes);
    }

    /**
     * 调用对象的成员方法(或类的类方法)
     *
     * @param object     具体的对象(或类)
     * @param methodName 对象的成员方法名称(或类的类方法名称)
     * @param argValues  方法的参数值列表
     * @param argTypes   方法的参数类型列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethod(Object object, String methodName, Object[] argValues, Class<?>[] argTypes) {
        try {
            return (E) getAccessibleMethod(object, methodName, argTypes).invoke(object, argValues);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取可访问的方法对象
     *
     * @param object     具体的对象(或类)
     * @param methodName 对象的成员方法名称(或类的类方法名称)
     * @param types      方法的参数类型列表
     * @return
     */
    private static Method getAccessibleMethod(Object object, String methodName, Class<?>... types) {
        Class<?> entityClass = object instanceof Class ? (Class<?>) object : object.getClass();
        while (entityClass != null) {
            try {
                Method target = entityClass.getDeclaredMethod(methodName, types);
                target.setAccessible(true);
                return target;
            } catch (Throwable e) {
            }
            entityClass = entityClass.getSuperclass();
        }
        return null;
    }

    /**
     * 把request中的请求参数转换成map
     *
     * @param request
     * @return
     */
    public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

    /**
     * 把request中的请求参数转换成map
     *
     * @param request
     * @return
     */
    public static Map<String, Object> convertRequestToMap(HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

    public static String getOrderNo(Long orderId) {
        String orderIdStr = orderId.toString();
        if (orderIdStr.length() < 2) {
            orderIdStr = "000" + orderIdStr;
        } else if (orderIdStr.length() < 3) {
            orderIdStr = "00" + orderIdStr;
        } else if (orderIdStr.length() < 4) {
            orderIdStr = "0" + orderIdStr;
        }
        return orderIdStr;
    }

    public static List<Long> stringToLongList(String strings) {
        String[] str = strings.split(",");
        List<Long> longs = Lists.newArrayList();
        for (String s : str) {
            longs.add(Long.parseLong(s));
        }
        return longs;
    }

    public static List<Integer> stringToIntegerList(String strings) {
        String[] str = strings.split(",");
        List<Integer> integers = Lists.newArrayList();
        for (String s : str) {
            integers.add(Integer.parseInt(s));
        }
        return integers;
    }

    public static List<String> stringToStringList(String strings) {
        String[] str = strings.split(",");
        List<String> stringList = Lists.newArrayList();
        for (String s : str) {
            stringList.add(s);
        }
        return stringList;
    }

}