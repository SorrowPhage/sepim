package com.sepim.springboot.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 账号生成
 */
public class AccountGenerateUtil {
    private static final String FIELD = "0123456789";
    private static final String SYMBOL= "sp";
    private static final Random RANDOM = new SecureRandom();

    public static String generateAccount() {
        char[] verCode = new char[7];
        for (int i = 0; i < verCode.length; i++) {
            verCode[i] = FIELD.charAt(RANDOM.nextInt(FIELD.length()));
        }
        return SYMBOL+String.valueOf(verCode);
    }
}
