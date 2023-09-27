package com.sepim.springboot.db;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: SorrowPhage
 * @date: 2023/9/20
 */
@Slf4j
public class DataSourceContextHolder {


    public static final String DEFAULT_DS = "sepimDB";

    //保存动态数据源名称
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    //设置数据源，决定当前线程使用那个数据源
    public static void setDB(String dbType) {
        log.info("切换到：" + dbType);
        CONTEXT_HOLDER.set(dbType);
    }

    //获取动态数据源名称
    public static String getDB() {
        return CONTEXT_HOLDER.get();
    }

    //移除当前数据源
    public static void clearDB() {
        CONTEXT_HOLDER.remove();
    }
}
