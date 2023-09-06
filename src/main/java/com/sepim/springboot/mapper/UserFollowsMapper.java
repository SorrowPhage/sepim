package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.UserFollows;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-08-17
 */
@Mapper
public interface UserFollowsMapper extends BaseMapper<UserFollows> {

    Integer getFansCount(Map<String,String> param);

    Integer getFollowCount(Map<String, String> param);

}
