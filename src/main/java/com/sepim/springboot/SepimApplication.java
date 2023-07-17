package com.sepim.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// @MapperScan("com.sepim.springboot.mapper")
@SpringBootApplication
public class SepimApplication {

    public static void main(String[] args) {
        SpringApplication.run(SepimApplication.class, args);
    }

}
