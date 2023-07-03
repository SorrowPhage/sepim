package com.sepim.springboot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StringRedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //    Redis的默认保存时间
    // @Value(value = "${redis.keepTime}")
    // private int redisKeepTime=5;
    private int redisKeepTime=5;

    /**
     * 添加数据到Redis中
     *
     * @param key
     * @param value
     */
    public void addRedis(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }


    /**
     * 添加数据到Redis中(自定义保存时间)
     *
     * @param key
     * @param value
     */
    public void addRedisTime(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, redisKeepTime, TimeUnit.MINUTES);
    }


    /**
     * 根据key获取Redis中数据
     *
     * @param key
     * @return
     */
    public String getRedis(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }


    /**
     * 根据key删除Redis中的数据
     *
     * @param key
     * @return
     */
    public boolean deleteRedis(String key) {
        Boolean result = stringRedisTemplate.delete(key);
        return result;
    }


    /**
     * 设置Redis的保存时间
     *
     * @param key
     * @param time
     */
    public void setRedisTime(String key, int time) {
        stringRedisTemplate.expire(key, time, TimeUnit.MINUTES);
    }
}
