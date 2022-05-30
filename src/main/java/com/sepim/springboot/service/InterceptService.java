package com.sepim.springboot.service;

import com.sepim.springboot.entity.ResultData;

public interface InterceptService {
    public ResultData verToken(String token);
}
