package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.vo.CzpIntoTribeTempVO;
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
public interface CzpIntoTribeTempMapper extends BaseMapper<CzpIntoTribeTemp> {

    List<String> batchNoList(String userId);

    List<CzpIntoTribeTempVO> getTempData(String userId, String batchNo);
}
