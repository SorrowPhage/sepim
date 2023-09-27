package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: SorrowPhage
 * @date: 2023/8/4
 */
@Data
public class FolderLike {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String folderId;

    private String userId;

    private Integer status;

    private String time;

    private String updateTime;

    public FolderLike() {
    }

    public FolderLike(String folderId, String userId, Integer status) {
        this.folderId = folderId;
        this.userId = userId;
        this.status = status;
    }

    public FolderLike(String folderId, String userId, Integer status, String updateTime) {
        this.folderId = folderId;
        this.userId = userId;
        this.status = status;
        this.updateTime = updateTime;
    }

    public FolderLike(String folderId, String userId, Integer status, String time, String updateTime) {
        this.folderId = folderId;
        this.userId = userId;
        this.status = status;
        this.time = time;
        this.updateTime = updateTime;
    }
}
