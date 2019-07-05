/**
 * Copyright (C), 2018-2018, 康之家有限公司
 * FileName: MybatisAop
 * Author:   admin
 * Date:     2018/7/16 14:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

//AOP拦截类，主要是为了createTime，updateTime，和主键id提供自动插入值得功能
@Aspect
@Component
@Order(5)//优先级
public class MybatisAop {

    @Pointcut("execution(public * com.kzj.drug.dao.*.*(..))")//切点
    public void drug() {
    }

    @Pointcut("execution(public * com.kzj.goods.dao.*.*(..))")//切点
    public void goods() {
    }

    @Before("drug()||goods()")//切入方法前
    public void doBefore(JoinPoint joinPoint) throws IllegalAccessException {
//        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        String methodName = joinPoint.getSignature().getName().toLowerCase();
        if (methodName.contains("insert")) {
            for (Object obj : joinPoint.getArgs()) {
                if (obj instanceof List) {
                    List<Object> list = (List) obj;
                    for (Object o : list) {
                        fieldSetValue(o, "insert");
                    }
                } else if (obj != null) {
                    fieldSetValue(obj, "insert");
                }
            }
        } else if (methodName.contains("update".toLowerCase())) {
            for (Object obj : joinPoint.getArgs()) {
                fieldSetValue(obj, "update");
            }
        }
    }

    private void fieldSetValue(Object o, String method) throws IllegalAccessException {
        if (method.equals("insert")) {
            for (Field field : o.getClass().getDeclaredFields()) {
                String fieldName = field.getName().toLowerCase();
                if (fieldName.contains("createtime") || fieldName.contains("updatetime")) {
                    field.setAccessible(true);
                    if (field.get(o) == null) {
                        field.set(o, System.currentTimeMillis());
                    }
                }
            }
        } else if (method.equals("update")) {
            for (Field field : o.getClass().getDeclaredFields()) {
                String fieldName = field.getName().toLowerCase();
                if (fieldName.contains("updatetime")) {
                    field.setAccessible(true);
                    field.set(o, System.currentTimeMillis());
                    break;
                }
            }
        }
    }

}
