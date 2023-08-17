package com.sepim.springboot.service.serviceimpl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.UserFollows;
import com.sepim.springboot.mapper.UserFollowsMapper;
import com.sepim.springboot.mapper.UserMapper;
import com.sepim.springboot.service.UserFollowsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-08-17
 */
@Service
@RequiredArgsConstructor
public class UserFollowsServiceImpl extends ServiceImpl<UserFollowsMapper, UserFollows> implements UserFollowsService {

    private final UserMapper userMapper;

    private final UserFollowsMapper userFollowsMapper;


    @Override
    public ResultData follow(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String userId = param.get("userId");
        String followedId = param.get("followedId");
        UserFollows userFollows = new UserFollows(userId, followedId, 1, LocalDateTimeUtil.now());
        QueryWrapper<UserFollows> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("followed_id", followedId);
        //关注时，之前可能没有关注数据，也可能有
        this.saveOrUpdate(userFollows, wrapper);
        resultData.setFlag("200");
        resultData.setData(null);
        return resultData;
    }

    @Override
    public ResultData unfollow(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String userId = param.get("userId");
        String followedId = param.get("followedId");
        UserFollows userFollows = new UserFollows(userId, followedId, 0, LocalDateTimeUtil.now());
        QueryWrapper<UserFollows> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("followed_id", followedId);
        this.update(userFollows, wrapper);
        resultData.setFlag("200");
        resultData.setData(null);
        return resultData;
    }

    @Override
    public ResultData followList(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String userId = param.get("userId");
        QueryWrapper<UserFollows> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("status", 1);
        List<UserFollows> list = this.list(wrapper);
        for (UserFollows userFollows : list) {
            userFollows.setUser(userMapper.followUser(userFollows.getFollowedId()));
        }
        resultData.setFlag("200");
        resultData.setData(list);
        return resultData;
    }

    @Override
    public ResultData fansList(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String followedId = param.get("followedId");
        QueryWrapper<UserFollows> wrapper = new QueryWrapper<>();
        wrapper.eq("followed_id", followedId).eq("status", 1);
        List<UserFollows> list = this.list(wrapper);
        for (UserFollows userFollows : list) {
            userFollows.setUser(userMapper.followUser(userFollows.getUserId()));
        }
        resultData.setFlag("200");
        resultData.setData(list);
        return resultData;
    }

    @Override
    public ResultData getFansNums(Map<String, String> param) {
        ResultData resultData = new ResultData();
        Integer fansCount = userFollowsMapper.getFansCount(param);
        resultData.setFlag("200");
        resultData.setData(fansCount);
        return resultData;
    }

    @Override
    public ResultData getFollowNUms(Map<String, String> param) {
        ResultData resultData = new ResultData();
        Integer followCount = userFollowsMapper.getFollowCount(param);
        resultData.setFlag("200");
        resultData.setData(followCount);
        return resultData;
    }

    @Override
    public ResultData getFollowStatus(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String userId = param.get("userId");
        String followedId = param.get("followedId");
        QueryWrapper<UserFollows> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("followed_id", followedId);
        UserFollows one = this.getOne(wrapper);
        if (one != null) {
            resultData.setFlag("200");
            resultData.setData(one.getStatus());
        } else {
            resultData.setFlag("400");
            resultData.setData(null);
        }
        return resultData;
    }

}
