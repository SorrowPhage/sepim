package com.sepim.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@TableName("czp_tribe")
@ApiModel(value = "CzpTribe对象", description = "")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CzpTribe implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("族群id")
    @TableId("id")
    private String id;

    @ApiModelProperty("族群名称")
    @TableField("tribe_name")
    private String tribeName;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("族群位置")
    @TableField("tribe_area")
    private String tribeArea;


}
