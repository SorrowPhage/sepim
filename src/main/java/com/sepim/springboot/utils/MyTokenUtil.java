package com.sepim.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sepim.springboot.entity.User;

import java.util.Date;

public class MyTokenUtil {
    public static String getToken(User user) {
        Date start = new Date();
        //有效时间
        //这部分应该保存到redis中
        //时间也应该在redis中设置
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(user.getId().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
