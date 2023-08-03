package com.sepim.springboot.entity;

import lombok.Data;

/**
 * @author: SorrowPhage
 * @date: 2023/8/2
 */
@Data
public class ChatList {
    private Integer id;

    private String ownId;

    private String otherId;
}
