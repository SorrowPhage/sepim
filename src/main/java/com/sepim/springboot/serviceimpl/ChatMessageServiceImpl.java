package com.sepim.springboot.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ChatMessage;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.mapper.ChatMessageMapper;
import com.sepim.springboot.service.ChatMessageService;
import com.sepim.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/4/24
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper,ChatMessage> implements ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultData resultData;

    /**
     * 找出用户的聊天信息
     * @param s1 用户1
     * @param s2 用户2
     * @return 返回搜素结果
     */
    @Override
    public ResultData getChatMessageList(String s1, String s2) {
        //在用户调用信息后，发给他的信息未读改为已读
        chatMessageMapper.updateMessageType(s1, s2);
        List<ChatMessage> chatMessage = chatMessageMapper.getChatMessage(s1, s2);
        for (ChatMessage message : chatMessage) {
            message.setUser(userService.getById(message.getFromId()));
        }
        resultData.setFlag("200");
        resultData.setData(chatMessage);
        return resultData;
    }

    /**
     * 增加一条信息
     * @param chatMessage 信息
     * @return 返回插入结果
     */
    @Override
    public ResultData insertChatMessage(ChatMessage chatMessage) {
        chatMessageMapper.updateIsLatest(chatMessage.getFromId(), chatMessage.getToId());
        chatMessageMapper.addChatMessage(chatMessage);
        resultData.setFlag("200");
        resultData.setData(chatMessage);
        return resultData;
    }


    /**
     * 找出user和交流过的其它用户的最后一条通信
     * @param user 用户的id
     * @return 返回搜素结果
     */
    @Override
    public ResultData getChatList(String user) {
        QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("is_latest", 1).eq("from_id", user).or().eq("to_id", user).eq("is_latest", 1);
        List<ChatMessage> list = this.list(wrapper);
        for (ChatMessage chatMessage : list) {
            String s = chatMessage.getFromId().equals(user) ? chatMessage.getToId() : chatMessage.getFromId();
            User userServiceById = userService.getById(s);
            chatMessage.setUser(userServiceById);
            chatMessage.setNoReadNum(chatMessageMapper.getNoReadChatMessage(user));
        }
        resultData.setFlag("200");
        resultData.setData(list);
        return resultData;
    }
}
