package com.sepim.springboot.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: SorrowPhage
 * @date: 2023/9/19
 */
public class DateUtil {

    public static final DateTimeFormatter SN_TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter SN_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter SN_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取系统时间
     * @return 系统时间
     */
    public static String getSystemDateTimeString() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    /**
     * 获取年月日信息
     * @return 年月日时间
     */
    public static String getSystemDateString() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    /**
     * 获取钟点信息
     * @return  钟点信息
     */
    public static String getSystemTimeString() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }


    public static String getSerialNumberDateString() {
        return LocalDateTime.now().format(SN_DATE_FORMATTER);
    }

    public static String getSerialNumberTimeString() {
        return LocalDateTime.now().format(SN_TIME_FORMATTER);
    }

    /**
     * @return 时间流水号
     */
    public static String getSerialNumberString() {
        return LocalDateTime.now().format(SN_DATE_TIME_FORMATTER);
    }

}
