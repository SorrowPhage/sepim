package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: SorrowPhage
 * @date: 2023/3/9
 */
@RestController
@RequiredArgsConstructor
public class RedisController {


    private final RedisService redisService;


    @PostMapping("/r/ping")
    public ResultData ping() {
        return redisService.ping();
    }

    @PostMapping("/r/set")
    public ResultData set() {
        return redisService.set();
    }

    @PostMapping("/r/get")
    public ResultData get() {
        return redisService.get();
    }
    @PostMapping("/r/setHaveTime")
    public ResultData setHaveTime() {
        return redisService.setHaveTime();
    }

    @PostMapping("/r/hasKey")
    public ResultData hasKey() {
        return redisService.hasKey();
    }

    @PostMapping("/r/incr")
    public ResultData incr() {
        return redisService.incr();
    }

    @PostMapping("/r/decr")
    public ResultData decr() {
        return redisService.decr();
    }

    @PostMapping("/r/delete")
    public ResultData delete() {
        return redisService.delete();
    }


}
