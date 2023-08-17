package com.sepim.springboot.utils;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
public class RedisKeyUtils {

    public static final String MAP_KEY_FOLDER_LIKED = "MAP_FOLDER_LIKED";

    public static final String MAP_KEY_FOLDER_LIKED_COUNT = "MAP_FOLDER_LIKED_COUNT";

    /**
     * 拼接文章id和用户id为key。
     * @param folderId 文章id
     * @param userId 用户id
     * @return key
     */
    public static String getLikedKey(String folderId, String userId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(folderId);
        stringBuilder.append("::");
        stringBuilder.append(userId);
        return stringBuilder.toString();
    }

}
