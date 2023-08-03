package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.ChatList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: SorrowPhage
 * @date: 2023/8/2
 */
@Mapper
public interface ChatListMapper extends BaseMapper<ChatList> {
}
