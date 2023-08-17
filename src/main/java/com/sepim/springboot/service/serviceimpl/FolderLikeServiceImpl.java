package com.sepim.springboot.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.FolderLike;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.mapper.FolderLikeMapper;
import com.sepim.springboot.service.FolderLikeRedisService;
import com.sepim.springboot.service.FolderLikeService;
import com.sepim.springboot.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FolderLikeServiceImpl extends ServiceImpl<FolderLikeMapper, FolderLike> implements FolderLikeService {

    private final FolderLikeRedisService redisService;

    private final FolderService folderService;

    @Override
    public ResultData like(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String folderId = param.get("folderId");
        String userId = param.get("userId");

        ResultData likeStatus1 = this.getLikeStatus(param);

        //为空表示没有相关数据，可以直接插入
        if (likeStatus1 == null) {
            redisService.saveLiked2Redis(folderId, userId);
            redisService.incrementLikedCount(folderId);
            return resultData;
        } else {
            Integer likeStatus = (Integer) this.getLikeStatus(param).getData();

            //只要不为 1 ，无论是null还是 0 都表示该用户没有对该文章点赞，都是可以点赞的
            // if (likeStatus != 1) {
            //
            // }
            if (likeStatus == null || likeStatus == 0) {
                redisService.saveLiked2Redis(folderId, userId);
                redisService.incrementLikedCount(folderId);
            }

            return resultData;
        }
    }

    @Override
    public ResultData unlike(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String folderId = param.get("folderId");
        String userId = param.get("userId");

        ResultData likeStatus1 = this.getLikeStatus(param);

        //为空表示没有相关数据，直接返回
        if (likeStatus1 == null) {
            resultData.setFlag("400");
            resultData.setData(null);
            return resultData;
        } else {
            Integer likeStatus = (Integer) this.getLikeStatus(param).getData();

            // 只要为 1 的时候，才表示可以取消点赞
            if (likeStatus == null || likeStatus == 0) {
                resultData.setFlag("400");
                resultData.setData(null);
                return resultData;
            } else if (likeStatus == 1) {
                redisService.unlikeFromRedis(folderId, userId);
                redisService.decrementLikedCount(folderId);
            }

            resultData.setFlag("200");
            resultData.setData(null);
            return resultData;
        }
    }

    @Override
    public ResultData getLikeStatus(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String folderId = param.get("folderId");
        String userId = param.get("userId");

        Integer liked2Redis = redisService.getLiked2Redis(folderId, userId);
        QueryWrapper<FolderLike> wrapper = new QueryWrapper<>();
        //Redis中不存在该数据
        if (liked2Redis == null) {
            //在数据库中获取该数据
            wrapper.eq("folder_id", folderId).eq("user_id", userId);
            FolderLike one = this.getOne(wrapper);
            if (one == null) {
                return null;
            } else if (one.getStatus() == 1) {
                resultData.setData(1);
            } else {
                resultData.setData(0);
            }
        } else if (liked2Redis == 1) {
            resultData.setData(1);
        } else {
            resultData.setData(0);
        }
        resultData.setFlag("200");
        return resultData;
    }

    @Override
    public ResultData getLikeCountRedis(Map<String, String> param) {
        ResultData resultData = new ResultData();
        String folderId = param.get("folderId");
        Integer likedCountFromRedis = redisService.getLikedCountFromRedis(folderId);
        if (likedCountFromRedis != null) {
            resultData.setData(likedCountFromRedis);
            resultData.setFlag("200");
        } else {
            resultData.setData(null);
            resultData.setFlag("400");
        }
        return resultData;
    }

    @Override
    public void transLikedFromRedis2DB() {
        List<FolderLike> list = redisService.getLikedFromRedis();
        for (FolderLike folderLike : list) {
            QueryWrapper<FolderLike> wrapper = new QueryWrapper<>();
            wrapper.eq("folder_id", folderLike.getFolderId()).eq("user_id", folderLike.getUserId());
            FolderLike fl = this.getOne(wrapper);
            if (fl == null) {
                this.save(folderLike);
            } else {
                this.updateById(fl);
            }
        }
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        List<Folder> list = redisService.getLikedCountFromRedis();
        for (Folder folder : list) {
            Folder folderById = folderService.getById(folder.getId());
            Integer likeNum = folder.getLikeCount() + folderById.getLikeCount();
            folderById.setLikeCount(likeNum);
            folderService.updateById(folderById);
        }
    }

}
