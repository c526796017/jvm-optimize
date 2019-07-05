/**
 * Copyright (C), 2018-2018, 康之家有限公司
 * FileName: DefaultLogAop
 * Author:   USER-PC
 * Date:     2018/8/27 10:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.common.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Order(5)
@Component
@Slf4j
public class DefaultLogAop {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Pointcut("execution(public * com.kzj.goods.controller..*.*(..))")
    public void goodsLog() {
    }

    @Pointcut("execution(public * com.kzj.drug.controller..*.*(..))")
    public void drugLog() {
    }

    @Before("drugLog()||goodsLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer sb = new StringBuffer();
        // 记录下请求内容
        sb.append("--url: ").append(request.getRequestURL()).append(" --ip: ")
                .append(request.getRemoteAddr()).append(" --method: ")
                .append(joinPoint.getSignature().getDeclaringTypeName()).append(".")
                .append(joinPoint.getSignature().getName()).append("访问开始时间：" + sdf.format(new Date()));
        // 获取参数, 只取自定义的参数, 自带的HttpServletRequest, HttpServletResponse不管
        if (joinPoint.getArgs().length > 0) {
            for (Object o : joinPoint.getArgs()) {
                if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    continue;
                }
                sb.append("--params: ").append(JSON.toJSONString(o));
                log.info(sb.toString());
            }
        }
    }

    @AfterReturning(returning = "ret", pointcut = "drugLog()||goodsLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        log.info("--response : " + JSON.toJSONString(ret) + "访问结束时间：" + sdf.format(new Date()));
    }

}
