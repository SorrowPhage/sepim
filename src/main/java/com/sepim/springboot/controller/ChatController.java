package com.sepim.springboot.controller;

import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: SorrowPhage
 * @date: 2023/4/24
 */
@RestController
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ResultData resultData;

    @GetMapping("/chat/get")
    public ResultData getChatMessageList(@RequestParam(value = "s1") String s1, @RequestParam(value = "s2") String s2) {
        resultData = chatMessageService.getChatMessageList(s1, s2);
        return resultData;
    }

    @GetMapping("/chat/chatlist")
    public ResultData getChatList(@RequestParam(value = "user") String user) {
        resultData = chatMessageService.getChatList(user);
        return resultData;
    }
}
