package com.sepim.springboot.entity.excelmodel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author: SorrowPhage
 * @date: 2023/9/8
 */
@Data
public class CzpUpload {

    @ExcelProperty(value = "用户id", index = 0)
    private String userId;

    @ExcelProperty(value = "父id", index = 1)
    private String parentId;
}
