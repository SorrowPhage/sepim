package com.sepim.springboot.serviceimpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.InterceptService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterceptServiceImpl implements InterceptService {

    private final UserService userService;


    /**
     * 验证用户的token是否合法
     * @param token token
     * @return 返回验证结果
     */
    @Override
    public ResultData verToken(String token) {
        ResultData resultData = new ResultData();
        if (token == null) {
            resultData.setFlag("token_null");
            resultData.setData(null);
            return resultData;
        }
        //获取token中的id
        String id = JWT.decode(token).getAudience().get(0);
        User user = userService.getById(id);
        if (user == null) {
            resultData.setFlag("user_no_exist");
            resultData.setData(null);
            return resultData;
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException j) {
            resultData.setFlag("user_ver_defeat");
            resultData.setData(null);
            return resultData;
        }
        resultData.setFlag("user_ver_succeed");
        user.setPassword(null);
        user.setToken(token);
        resultData.setData(user);
        return resultData;
    }
}
