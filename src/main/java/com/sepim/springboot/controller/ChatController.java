package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: SorrowPhage
 * @date: 2023/4/24
 */
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    /**
     * 找出两个用户之间的聊天信息
     *
     * @param s1 用户1
     * @param s2 用户2
     * @return 搜索结果
     */
    @GetMapping("/chat/get")
    public ResultData getChatMessageList(@RequestParam(value = "s1") String s1, @RequestParam(value = "s2") String s2) {
        return chatMessageService.getChatMessageList(s1, s2);
    }

    /**
     * 获取用户和其它用户之间的最后一条信息
     * @param user 用户
     * @return 搜索结果
     */
    @GetMapping("/chat/chatlist")
    public ResultData getChatList(@RequestParam(value = "user") String user) {
        // return chatMessageService.getChatList(user);
        return chatMessageService.getChatListByChatLIst(user);
    }
}
