package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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


    private Integer id;


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
