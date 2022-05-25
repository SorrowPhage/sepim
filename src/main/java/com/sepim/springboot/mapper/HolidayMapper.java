package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.Holiday;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HolidayMapper extends BaseMapper<Holiday> {
}
