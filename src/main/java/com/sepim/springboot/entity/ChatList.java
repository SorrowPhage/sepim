package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: SorrowPhage
 * @date: 2023/8/2
 */
@Data
public class ChatList {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String ownId;

    private String otherId;
}
