package com.sepim.springboot.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sepim.springboot.entity.CzpUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/9/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CzpUserVO {

    private String id;

    private String name;

    private String sex;

    private String areaId;

    private String parentId;

    private String rootId;

    private String birthday;

    private String areaName;

    private String des;

    private String mateId;

    private CzpUser mate;

    private String avatar;

    private String createrId;

    private String tribeName;

    private String tribeArea;

    private List<CzpUserVO> children = new ArrayList<>();

}
