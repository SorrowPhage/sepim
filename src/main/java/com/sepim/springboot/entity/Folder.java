package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    private String id;
    private String userId;
    private String title;
    private String roughly;
    private int readNum;
    private String type;
    private String mdUrl;
    private String time;
    private Integer likeCount;
    @TableField(exist = false)
    private String content;
    @TableField(exist = false)
    private String mdContent;
    @TableField(exist = false)
    private List<Comment> comments;
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private String status;
    @TableField(exist = false)
    private Integer likeNum;

    public Folder(String id, Integer likeCount) {
        this.id = id;
        this.likeCount = likeCount;
    }
}
