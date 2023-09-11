package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("czp_user")
@ApiModel(value = "CzpUser对象", description = "")
public class CzpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("所属地")
    @TableField("area_id")
    private String areaId;

    @ApiModelProperty("父级")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("根级")
    @TableField("root_id")
    private String rootId;

    @ApiModelProperty("出生日期")
    @TableField("birthday")
    private String birthday;

    @TableField("mate_id")
    private String mateId;

    @TableField("avatar")
    private String avatar;

    @TableField("small_group")
    private String smallGroup;



}
