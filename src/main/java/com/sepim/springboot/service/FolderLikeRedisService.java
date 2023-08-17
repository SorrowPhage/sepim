package com.sepim.springboot.service;

import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.FolderLike;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
public interface FolderLikeRedisService{

    /**
     * 点赞，状态为1
     * @param folderId 被点赞的文章
     * @param userId 点赞的用户
     */
    void saveLiked2Redis(String folderId,String userId);

    /**
     *取消点赞，状态为 0
     * @param folderId 被点赞的文章
     * @param userid 点赞的用户
     */
    void unlikeFromRedis(String folderId,String userid);

    void deleteLikedFromRedis(String folderId, String userId);

    /**
     * 获取该用户对文章的点赞状态
     * @param folderId 文章id
     * @param userId 用户id
     * @return 返回对象为null时表示近一个小时没有操作
     */
    Integer getLiked2Redis(String folderId, String userId);

    /**
     * 获取该用户对文章的点赞数量
     * @param folderId 文章id
     * @return 返回对象为null时表示近一个小时没有操作
     */
    Integer getLikedCountFromRedis(String folderId);

    /**
     * 文章点赞数加1
     * @param folderId 文章id
     */
    void incrementLikedCount(String folderId);

    /**
     * 文章点赞数减1
     * @param folderId 文章id
     * @return 返回结果
     */
    void decrementLikedCount(String folderId);


    /**
     * 获取点赞数据
     * @return 返回获取数据
     */
    List<FolderLike> getLikedFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return 点赞数量
     */
    List<Folder> getLikedCountFromRedis();

}
