package com.sepim.springboot.service.serviceimpl;

import com.sepim.springboot.entity.Folder;
import com.sepim.springboot.entity.FolderLike;
import com.sepim.springboot.entity.FolderLikeEnum;
import com.sepim.springboot.service.FolderLikeRedisService;
import com.sepim.springboot.utils.RedisKeyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FolderLikeRedisServiceImpl implements FolderLikeRedisService {

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public void saveLiked2Redis(String folderId, String userId) {
        String likedKey = RedisKeyUtils.getLikedKey(folderId, userId);
        //===========================Integer转String问题
        stringRedisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_FOLDER_LIKED,likedKey, String.valueOf(FolderLikeEnum.LIKE.getCode()));
    }

    @Override
    public void unlikeFromRedis(String folderId, String userid) {
        String likedKey = RedisKeyUtils.getLikedKey(folderId, userid);
        stringRedisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_FOLDER_LIKED, likedKey, String.valueOf(FolderLikeEnum.UNLIKE.getCode()));
    }

    @Override
    public void deleteLikedFromRedis(String folderId, String userId) {
        String likedKey = RedisKeyUtils.getLikedKey(folderId, userId);
        stringRedisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_FOLDER_LIKED, likedKey);
    }

    @Override
    public Integer getLiked2Redis(String folderId, String userId) {
        String likedKey = RedisKeyUtils.getLikedKey(folderId, userId);
        Object o = stringRedisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_FOLDER_LIKED, likedKey);
        if (o != null) {
            return Integer.parseInt((String) o);
        } else {
            return null;
        }
    }

    @Override
    public Integer getLikedCountFromRedis(String folderId) {
        Object o = stringRedisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_FOLDER_LIKED_COUNT, folderId);
        if (o != null) {
            return Integer.parseInt((String) o);
        } else {
            return null;
        }
    }

    @Override
    public void incrementLikedCount(String folderId) {
        stringRedisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_FOLDER_LIKED_COUNT, folderId, +1);
    }

    @Override
    public void decrementLikedCount(String folderId) {
        stringRedisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_FOLDER_LIKED_COUNT, folderId, -1);
    }



    @Override
    public List<FolderLike> getLikedFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = stringRedisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_FOLDER_LIKED, ScanOptions.NONE);
        List<FolderLike> likes = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String folderId = split[0];
            String userId = split[1];
            Integer value = Integer.parseInt((String) entry.getValue());
            FolderLike folderLike = new FolderLike(folderId, userId, value);
            likes.add(folderLike);
            stringRedisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_FOLDER_LIKED, key);
        }
        return likes;
    }

    @Override
    public List<Folder> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = stringRedisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_FOLDER_LIKED_COUNT, ScanOptions.NONE);
        List<Folder> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Folder folder = new Folder(key, Integer.parseInt((String) entry.getValue()));
            list.add(folder);
            stringRedisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_FOLDER_LIKED_COUNT, key);
        }
        return list;
    }
}
