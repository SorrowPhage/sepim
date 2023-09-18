package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.CzpTribe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-08
 */
@Mapper
public interface CzpTribeMapper extends BaseMapper<CzpTribe> {

    void deleteBatch(CzpTribe czpTribe);

    void dleteByBatch(List<CzpTribe> list);

}
