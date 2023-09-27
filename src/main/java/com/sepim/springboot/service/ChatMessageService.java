package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ChatMessage;
import com.sepim.springboot.entity.ResultData;
import org.springframework.stereotype.Service;

public interface ChatMessageService extends IService<ChatMessage> {

    ResultData getChatMessageList(String s1, String s2);

    void insertChatMessage(ChatMessage chatMessage);

    ResultData getChatList(String user);

    ResultData getChatListByChatLIst(String user);


}
