package com.sepim.springboot.entity;

import lombok.Data;

/**
 * @author: SorrowPhage
 * @date: 2023/2/24
 */
@Data
public class MusicList {
    private String id;
    private String name;
    private String describe;
    private String listImage;
    private String owner;
}
