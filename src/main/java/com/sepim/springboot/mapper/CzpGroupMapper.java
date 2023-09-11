package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.CzpGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
@Mapper
public interface CzpGroupMapper extends BaseMapper<CzpGroup> {

    List<String> getIdList(Map<String, String> param);

    List<String> getCreater(Map<String, String> param);

}
