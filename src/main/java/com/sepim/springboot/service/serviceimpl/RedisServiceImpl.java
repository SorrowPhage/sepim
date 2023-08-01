package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.RedisService;
import com.sepim.springboot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author: SorrowPhage
 * @date: 2023/3/9
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ResultData resultData;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Override
    public ResultData ping() {
        resultData.setData(redisUtil.ping());
        resultData.setFlag("200");
        return resultData;
    }

    @Override
    public ResultData set() {
        String key = "one";
        Integer value = 1;
        boolean set = redisUtil.set(key, value);
        String res = host + ":" + port +"> set " + key + " " + value;
        if (set){
            res += "\nOK";
            resultData.setData(res);
            resultData.setFlag("200");
            return resultData;
        }else{
            res += "\nNO";
            resultData.setData(res);
            resultData.setFlag("400");
            return resultData;
        }
    }

    @Override
    public ResultData get() {
        Object one = redisUtil.get("one");
        String res = host + ":" + port +"> get one";
        if (one != null) {
            Integer o = (Integer) one;
            res += "\n" + o;
            resultData.setData(res);
            resultData.setFlag("200");
            return resultData;
        } else {
            res += "\nNo exist" ;
            resultData.setData(res);
            resultData.setFlag("400");
            return resultData;
        }

    }

    @Override
    public ResultData setHaveTime() {
        boolean two = redisUtil.setRedis("two", 1L, 60L);
        String res = host + ":" + port +"> expire two 60\n";
        if (two) {
            res += "OK";
            resultData.setData(res);
            resultData.setFlag("200");
            return resultData;
        } else {
            res += "NO";
            resultData.setData(res);
            resultData.setFlag("400");
            return resultData;
        }
    }

    @Override
    public ResultData hasKey() {
        boolean one = redisUtil.hasKey("one");
        boolean two = redisUtil.hasKey("two");
        String res = host + ":" + port + "> exists one \n";
        if (one) {
            res += "exists";
        } else {
            res += "no exists";
        }
        res += "\n" + host + ":" + port + "> exists two \n";
        if (two) {
            res += "exists";
        } else {
            res += "no exists";
        }
        resultData.setData(res);
        resultData.setFlag("200");
        return resultData;
    }

    @Override
    public ResultData incr() {
        long one = redisUtil.incr("one", 1L);
        String res = host + ":" + port +"> incr one 1\n"+one;
        resultData.setData(res);
        resultData.setFlag("200");
        return resultData;
    }

    @Override
    public ResultData decr() {
        long one = redisUtil.decr("one", 1L);
        String res = host + ":" + port + "> decr one 1\n" + one;
        resultData.setData(res);
        resultData.setFlag("200");
        return resultData;
    }

    @Override
    public ResultData delete() {
        boolean one = redisUtil.delete("one");
        String res = host + ":" + port + "> delete one \n";
        if (one) {
            res += "OK";
            resultData.setData(res);
            resultData.setFlag("200");
            return resultData;
        } else {
            res += "No";
            resultData.setData(res);
            resultData.setFlag("400");
            return resultData;
        }
    }
}
