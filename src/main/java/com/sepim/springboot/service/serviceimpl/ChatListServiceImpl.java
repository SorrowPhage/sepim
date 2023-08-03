package com.sepim.springboot.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.ChatList;
import com.sepim.springboot.mapper.ChatListMapper;
import com.sepim.springboot.service.ChatListService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/8/2
 */
@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements ChatListService {


    @Override
    public boolean getChatPartner(String ownId, String otherId) {
        QueryWrapper<ChatList> wrapper = new QueryWrapper<>();
        wrapper.eq("own_id", ownId).eq("other_id", otherId);
        ChatList one = this.getOne(wrapper);
        return one != null;
    }

    @Override
    public boolean insertChatPartner(String ownId, String otherId) {
        ChatList chatList = new ChatList();
        chatList.setOwnId(ownId);
        chatList.setOtherId(otherId);
        return this.save(chatList);
    }

    @Override
    public boolean deleteChatPartner(String ownId, String otherId) {
        QueryWrapper<ChatList> wrapper = new QueryWrapper<>();
        wrapper.eq("own_id", ownId).eq("other_id", otherId);
        return this.remove(wrapper);
    }

    @Override
    public List<ChatList> getOtherList(String ownId) {
        QueryWrapper<ChatList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("own_id", ownId);
        return this.list(queryWrapper);
    }
}
