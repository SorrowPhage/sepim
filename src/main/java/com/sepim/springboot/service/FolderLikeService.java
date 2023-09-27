package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.FolderLike;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.ResultMessage;

import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
public interface FolderLikeService  extends IService<FolderLike> {


    /**
     * 点赞
     * @param param 点赞参数
     * @return 返回点赞结果
     */
    ResultData like(Map<String, String> param);

    ResultMessage liek2DB(Map<String, String> param);

    /**
     * 取消点赞
     * @param param 点赞参数
     * @return 取消点赞结果
     */
    ResultData unlike(Map<String, String> param);

    ResultMessage unlike2DB(Map<String, String> param);

    /**
     * 获取用户对某篇文章的点赞状态
     * @param param 文章id，用户id
     * @return 1表示已点赞，0表示为点赞,null表示在redis中和数据库中都没有数据，即该用户没有对该文章有过点赞操作
     */
    ResultData getLikeStatus(Map<String, String> param);

    ResultData getLikeStatus2DB(Map<String, String> param);

    /**
     * 获取文章点赞数量
     * @param param 文章id
     * @return redis中的文章点赞数量
     */
    ResultData getLikeCountRedis(Map<String, String> param);

    /**
     * 将点赞记录从redis中保证到数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将点赞数量保存到数据库中
     */
    void transLikedCountFromRedis2DB();

}
