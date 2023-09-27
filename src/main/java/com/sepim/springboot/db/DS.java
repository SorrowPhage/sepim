package com.sepim.springboot.db;

import java.lang.annotation.*;

/**
 * @author: SorrowPhage
 * @date: 2023/9/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DS {
    String value() default "czpDB";
}
