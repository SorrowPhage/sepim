package com.sepim.springboot.entity;

import lombok.Data;

/**
 * @author: SorrowPhage
 * @date: 2023/2/21
 */
@Data
public class Music {
    private String id;
    private String musicName;
    private String singer;
    private String musicUrl;
    private String uploadTime;
    private String pictureUrl;
    private String serverUrl;
    private String lrc;
}
