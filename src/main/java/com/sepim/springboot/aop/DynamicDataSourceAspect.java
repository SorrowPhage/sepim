package com.sepim.springboot.aop;

import com.sepim.springboot.db.DS;
import com.sepim.springboot.db.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: SorrowPhage
 * @date: 2023/9/20
 */
@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {


    @Before("@annotation(com.sepim.springboot.db.DS)")
    public void beforeChangingOverDataSource(JoinPoint joinPoint) {
        Class<?> className = joinPoint.getSignature().getDeclaringType();
        String methodName = joinPoint.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            Method method = className.getMethod(methodName, parameterTypes);
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                dataSource = annotation.value();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        DataSourceContextHolder.setDB(dataSource);
    }

    @After("@annotation(com.sepim.springboot.db.DS)")
    public void afterChangingOverDataSource() {
        DataSourceContextHolder.clearDB();
    }
}
