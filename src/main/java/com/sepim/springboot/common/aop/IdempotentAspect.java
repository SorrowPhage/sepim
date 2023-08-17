package com.sepim.springboot.common.aop;

import com.sepim.springboot.utils.RedisLockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author: SorrowPhage
 * @date: 2023/8/11
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentAspect {

    private final RedisLockUtils redisLockUtils;

    @Pointcut("@annotation(com.sepim.springboot.common.aop.Idempotent)")
    public void idempotent() {}

    @Around("idempotent()")
    public Object idempotentAround(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        int lockTime = idempotent.lockTime();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        String requestURI = request.getRequestURI();
        String idKey = getIdKey(token, requestURI);
        String uuid = UUID.randomUUID().toString();
        //获取锁
        boolean lock = redisLockUtils.tryLock(idKey, uuid, lockTime);
        log.info("tryLock key =[{}] ,id=[{}]", idKey, uuid);
        if (lock) {
            log.info("tyrLock success,key =[{}] ,id=[{}]", idKey, uuid);
            Object result = null;
            try {
                result = joinPoint.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        } else {
            log.info("tryLock fail,key=[{}]", idKey);
            // throw new RuntimeException("请求重复，请稍后再试");
            log.info("请求重复，请稍后在尝试");
            return null;
        }
    }

    public String getIdKey(String token, String requestURI) {
        return "redisLock:" + token + requestURI;
    }


}
