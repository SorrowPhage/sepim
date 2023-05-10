package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ChatMessage;
import com.sepim.springboot.entity.ResultData;

public interface ChatMessageService extends IService<ChatMessage> {

    public ResultData getChatMessageList(String s1, String s2);

    ResultData insertChatMessage(ChatMessage chatMessage);

    public ResultData getChatList(String user);


}
