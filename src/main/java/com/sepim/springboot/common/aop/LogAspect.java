package com.sepim.springboot.common.aop;

import com.alibaba.fastjson.JSON;
import com.sepim.springboot.utils.HttpContextUtil;
import com.sepim.springboot.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author: SorrowPhage
 * @date: 2023/5/19
 */
@Component
@Aspect //切面，定义通知和切点的关系
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.sepim.springboot.common.aop.LogAnnotation)")
    public void pt(){}

    //环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        recordLog(joinPoint,endTime-startTime);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint,long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=========================start===========================");
        log.info("module:{}", logAnnotation.module());
        log.info("operator:{}", logAnnotation.operator());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

        //请求的参数
        Object args[] = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}", params);

        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        log.info("ip:{}", IPUtils.getIpAddr(request));

        log.info("excute time:{} ms", time);
        log.info("=========================end===========================");
    }

}
