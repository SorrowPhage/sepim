package com.sepim.springboot.utils;

import org.junit.jupiter.api.Test;

public class VerCodeGenerateUtiTest {
    @Test
    void test(){
//        System.out.println(VerCodeGenerateUtil.generateVerCode());
        System.out.println(AccountGenerateUtil.generateAccount());
    }

    @Test
    void testUpload(){
        String avatar_url = "http://localhost:8088/upload/sepim/avatar/b6b78bfdc28f4f4485cbabba5d45c547function.ico";
        FileUploadUtil.delete(avatar_url);
    }
}
