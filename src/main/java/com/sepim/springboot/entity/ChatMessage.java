package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author: SorrowPhage
 * @date: 2023/4/24
 */
@Data
@Component
public class ChatMessage {




    // private String linkId;

    private String fromId;

    private String toId;

    private String content;

    private Timestamp sendTime;

    private Integer type;

    private Integer isLatest;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Integer noReadNum;

}
