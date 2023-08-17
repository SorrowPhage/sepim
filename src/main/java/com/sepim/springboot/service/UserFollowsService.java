package com.sepim.springboot.service;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.UserFollows;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-08-17
 */
public interface UserFollowsService extends IService<UserFollows> {

    /**
     * 关注
     *
     * @param param userId,followedId
     * @return
     */
    ResultData follow(Map<String, String> param);

    /**
     * 取消关注
     *
     * @param param userId,followedId
     * @return
     */
    ResultData unfollow(Map<String, String> param);

    /**
     * 关注列表
     *
     * @param param userId
     * @return
     */
    ResultData followList(Map<String, String> param);

    /**
     * 粉丝列表
     *
     * @param param followedId
     * @return
     */
    ResultData fansList(Map<String, String> param);

    /**
     * 获取粉丝数量
     *
     * @param param
     * @return
     */
    ResultData getFansNums(Map<String,String> param);

    /**
     * 获取关注数量
     *
     * @param param
     * @return
     */
    ResultData getFollowNUms(Map<String, String> param);

    /**
     * 获取关注状态
     * @param param userId,followedId
     * @return
     */
    ResultData getFollowStatus(Map<String, String> param);

}
