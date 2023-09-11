package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
@Getter
@Setter
@TableName("czp_group")
@ApiModel(value = "CzpGroup对象", description = "")
public class CzpGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("creater_id")
    private String createrId;

    @TableField("create_time")
    private String createTime;

    @TableField("group_name")
    private String groupName;

    @TableField("parent_id")
    private String parentId;


}
