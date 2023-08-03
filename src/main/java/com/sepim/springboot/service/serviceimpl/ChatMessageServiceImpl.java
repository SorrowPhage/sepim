package com.sepim.springboot.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ChatList;
import com.sepim.springboot.entity.ChatMessage;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.entity.User;
import com.sepim.springboot.mapper.ChatMessageMapper;
import com.sepim.springboot.service.ChatListService;
import com.sepim.springboot.service.ChatMessageService;
import com.sepim.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/4/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage> implements ChatMessageService {

    private final ChatListService chatListService;

    private final ChatMessageMapper chatMessageMapper;

    private final UserService userService;


    /**
     * 找出用户的聊天信息
     *
     * @param s1 用户1
     * @param s2 用户2
     * @return 返回搜素结果
     */
    @Override
    public ResultData getChatMessageList(String s1, String s2) {
        ResultData resultData = new ResultData();
        //在用户调用信息后，发给他的信息未读改为已读
        chatMessageMapper.updateMessageType(s1, s2);
        List<ChatMessage> chatMessage = chatMessageMapper.getChatMessage(s1, s2);

        User user1 = userService.getById(s1);
        User user2 = userService.getById(s2);

        for (ChatMessage message : chatMessage) {
            if (message.getFromId().equals(s1)) {
                message.setUser(user1);
            } else {
                message.setUser(user2);
            }
        }

        resultData.setFlag("200");
        resultData.setData(chatMessage);
        return resultData;
    }

    /**
     * 增加一条信息
     *
     * @param chatMessage 信息
     */
    @Override
    public void insertChatMessage(ChatMessage chatMessage) {
        ResultData resultData = new ResultData();
        chatMessageMapper.updateIsLatest(chatMessage.getFromId(), chatMessage.getToId());
        chatMessageMapper.addChatMessage(chatMessage);
        resultData.setFlag("200");
        resultData.setData(chatMessage);
    }


    /**
     * 找出user和交流过的其它用户的最后一条通信以及未阅读的信息的数量
     *
     * @param user 用户的id
     * @return 返回搜素结果
     */
    @Override
    public ResultData getChatList(String user) {
        ResultData resultData = new ResultData();
        QueryWrapper<ChatMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("is_latest", 1).eq("from_id", user).or().eq("to_id", user).eq("is_latest", 1);
        List<ChatMessage> list = this.list(wrapper);
        for (ChatMessage chatMessage : list) {
            String s = chatMessage.getFromId().equals(user) ? chatMessage.getToId() : chatMessage.getFromId();
            chatMessage.setUser(userService.getById(s));
            chatMessage.setNoReadNum(chatMessageMapper.getNoReadNumLatestChatMessage(s, user));
        }
        resultData.setFlag("200");
        resultData.setData(list);
        return resultData;
    }

    @Override
    public ResultData getChatListByChatLIst(String user) {
        ResultData resultData = new ResultData();

        List<ChatList> otherList = chatListService.getOtherList(user);
        List<ChatMessage> chatMessageList = new ArrayList<>(otherList.size());
        for (ChatList chatList : otherList) {
            QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_latest", 1).in("from_id", user, chatList.getOtherId()).in("to_id", user, chatList.getOtherId());
            ChatMessage one = this.getOne(queryWrapper);
            String s = one.getFromId().equals(user) ? one.getToId() : one.getFromId();
            one.setUser(userService.getById(s));
            one.setNoReadNum(chatMessageMapper.getNoReadNumLatestChatMessage(s, user));
            chatMessageList.add(one);
        }
        resultData.setData(chatMessageList);
        resultData.setFlag("200");
        return resultData;
    }
}
