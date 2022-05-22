package com.sepim.springboot.utils;


import java.security.SecureRandom;
import java.util.Random;

/**
 * 生成验证码
 */
public class VerCodeGenerateUtil {
    private static final String FIELD = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String generateVerCode() {
        char[] verCode = new char[6];
        for (int i = 0; i < verCode.length; i++) {
            verCode[i] = FIELD.charAt(RANDOM.nextInt(FIELD.length()));
        }
        return String.valueOf(verCode);
    };

}
