package com.sepim.springboot.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: SorrowPhage
 * @date: 2023/8/11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 设置锁定时间,超时后自动返回
     * @return
     */
    int lockTime() default 10;

}
