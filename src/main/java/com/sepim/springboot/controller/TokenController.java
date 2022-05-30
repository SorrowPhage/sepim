package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.service.InterceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private InterceptService interceptService;

    @Autowired
    private ResultData resultData;

    @PostMapping("/ver/token")
    public ResultData verToken(@RequestBody User user) {
        resultData = interceptService.verToken(user.getToken());
        return resultData;
    }
}
