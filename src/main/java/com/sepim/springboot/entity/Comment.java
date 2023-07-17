package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Comment {
    private String id;
    private String folderId;
    private String parentId;
    private String rootId;
    private String content;
    private String username;
    private String account;
    private String avatarUrl;
    private String data;

    @TableField(exist = false)
    private List<Comment> child;
    @TableField(exist = false)
    private String parentUsername;
    @TableField(exist = false)
    private String parentAccount;
    @TableField(exist = false)
    private User user;
}
