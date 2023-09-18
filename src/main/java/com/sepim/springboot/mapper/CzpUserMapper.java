package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.CzpUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.vo.CzpUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-06
 */
@Mapper
public interface CzpUserMapper extends BaseMapper<CzpUser> {

    List<CzpUserVO> relationChat2Area(Map<String, String> param);

    List<CzpUserVO> relationChat2Area(String smallGroup);

    List<CzpUserVO> relationChat2All(List<String> list);

    List<CzpUserVO> relationChat2Tribe(Map<String, String> param);

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    CzpUserVO getUser(String id);

    List<CzpUserVO> offstringList(String id);

}
