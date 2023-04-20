package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: SorrowPhage
 * @date: 2023/3/9
 */
@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ResultData resultData;

    @PostMapping("/r/ping")
    public ResultData ping() {
        resultData = redisService.ping();
        return resultData;
    }

    @PostMapping("/r/set")
    public ResultData set() {
        resultData = redisService.set();
        return resultData;
    }

    @PostMapping("/r/get")
    public ResultData get() {
        resultData = redisService.get();
        return resultData;
    }
    @PostMapping("/r/setHaveTime")
    public ResultData setHaveTime() {
        resultData = redisService.setHaveTime();
        return resultData;
    }

    @PostMapping("/r/hasKey")
    public ResultData hasKey() {
        resultData = redisService.hasKey();
        return resultData;
    }

    @PostMapping("/r/incr")
    public ResultData incr() {
        resultData = redisService.incr();
        return resultData;
    }

    @PostMapping("/r/decr")
    public ResultData decr() {
        resultData = redisService.decr();
        return resultData;
    }

    @PostMapping("/r/delete")
    public ResultData delete() {
        resultData = redisService.delete();
        return resultData;
    }


}
