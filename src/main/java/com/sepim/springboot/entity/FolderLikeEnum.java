package com.sepim.springboot.entity;

import lombok.Getter;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
@Getter
public enum FolderLikeEnum {

    LIKE(1,"点赞"),
    UNLIKE(0,"取消点赞");

    private Integer code;

    private String msg;

    FolderLikeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
