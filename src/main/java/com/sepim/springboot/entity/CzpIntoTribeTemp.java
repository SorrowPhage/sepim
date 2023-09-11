package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("czp_into_tribe_temp")
@ApiModel(value = "CzpIntoTribeTemp对象", description = "")
public class CzpIntoTribeTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private String userId;

    @TableField("parent_id")
    private String parentId;

    @TableField("operater_id")
    private String operaterId;

    @TableField("operater_time")
    private String operaterTime;

    @TableField("batch_no")
    private String batchNo;


}
