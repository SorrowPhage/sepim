package com.sepim.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sepim.springboot.entity.ChatList;

import java.util.List;

/**
 * @author: SorrowPhage
 * @date: 2023/8/2
 */
public interface ChatListService extends IService<ChatList> {

    /**
     * 获取聊天对象
     * @param ownId 获取人
     * @param otherId 其他人
     * @return true表示有该对象，false表示没有该对象
     */
    boolean getChatPartner(String ownId, String otherId);


    /**
     * 插入新的聊天对象
     * @param ownId 本人
     * @param otherId 其他人
     * @return
     */
    boolean insertChatPartner(String ownId, String otherId);


    /**
     * 删除聊天对象
     * @param ownId 本人
     * @param otherId 其他人
     * @return
     */
    boolean deleteChatPartner(String ownId, String otherId);


    /**
     * 获取聊天列表
     * @param ownId 本人
     * @return 返回数据
     */
    List<ChatList> getOtherList(String ownId);
}
