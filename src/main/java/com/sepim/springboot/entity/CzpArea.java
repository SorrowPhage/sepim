package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-06
 */
@Getter
@Setter
@TableName("czp_area")
@ApiModel(value = "CzpArea对象", description = "")
public class CzpArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("area_id")
    private String areaId;

    @TableField("area_name")
    private String areaName;

    @TableField("des")
    private String des;


}
