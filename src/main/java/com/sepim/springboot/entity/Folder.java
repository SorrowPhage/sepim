package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Folder {
    private String id;
    private String userId;
    private String username;
    private String title;
    private String roughly;
    private int readNum;
    private String type;
    private String url;
    private String state;
    private String mdUrl;
    @TableField(exist = false)
    private String content;
    @TableField(exist = false)
    private String mdContent;
    @TableField(exist = false)
    private List<Comment> comments;
}
