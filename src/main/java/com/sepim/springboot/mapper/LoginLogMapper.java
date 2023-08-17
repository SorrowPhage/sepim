package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.LoginLog;
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
 * @since 2023-08-15
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    List<LoginLog> getRecent14Data(Map<String,String> param);

}
