package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.InterceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final InterceptService interceptService;

    /**
     * 验证token
     * @param user 用户token信息
     * @return 返回验证结果
     */
    @PostMapping("/ver/token")
    public ResultData verToken(@RequestBody User user) {
        return interceptService.verToken(user.getToken());
    }

}
