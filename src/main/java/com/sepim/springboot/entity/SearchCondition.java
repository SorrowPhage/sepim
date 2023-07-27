package com.sepim.springboot.entity;

import lombok.Data;

/**
 * @author: SorrowPhage
 * @date: 2023/6/14
 */
@Data
public class SearchCondition {
    private String title;
    private Integer pageIndex;
    private Integer pageSize;
}
