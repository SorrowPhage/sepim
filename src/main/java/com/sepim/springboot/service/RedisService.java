package com.sepim.springboot.service;


import com.sepim.springboot.entity.ResultData;

public interface RedisService {

    ResultData ping();

    ResultData set();

    ResultData get();

    ResultData setHaveTime();

    ResultData hasKey();

    ResultData incr();

    ResultData decr();

    ResultData delete();

}
