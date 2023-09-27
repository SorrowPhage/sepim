package com.sepim.springboot.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/9/20
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "sepimDB")
    @ConfigurationProperties("spring.datasource.sepim")
    public DataSource sepimDB() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "czpDB")
    @ConfigurationProperties("spring.datasource.czp")
    public DataSource czpDB() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(sepimDB());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put("sepimDB", sepimDB());
        dsMap.put("czpDB", czpDB());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }


}
