package com.sepim.springboot.common.aop;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogExecuteMethod {

    String module() default "";

    String operator() default "";

}