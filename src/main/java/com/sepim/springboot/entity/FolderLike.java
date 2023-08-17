package com.sepim.springboot.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
@Data
public class FolderLike {

    private Integer id;

    private String folderId;

    private String userId;

    private Integer status;

    private Timestamp time;

    public FolderLike() {
    }

    public FolderLike(String folderId, String userId, Integer status) {
        this.folderId = folderId;
        this.userId = userId;
        this.status = status;
    }
}
