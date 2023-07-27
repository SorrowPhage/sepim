package com.sepim.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sepim.springboot.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    List<ChatMessage> getChatMessage(String s1,String s2);

    int addChatMessage(ChatMessage chatMessage);

    int updateIsLatest(String s1, String s2);

    int updateMessageType(String s1, String s2);

    int getNoReadChatMessage(String s);

    int getNoReadNumLatestChatMessage(String s1, String user);

}
