package com.sepim.springboot.config;

import com.sepim.springboot.controller.WebSocket;
import com.sepim.springboot.service.ChatMessageService;
import com.sepim.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author: SorrowPhage
 * @date: 2023/4/25
 */
@Configuration
public class WebsocketConfiguration {
    //添加下面配置 在socket引入socketUserService
    @Autowired
    public void chatMessageService(ChatMessageService chatMessageService){
        WebSocket.chatMessageService= chatMessageService;
    }

    @Autowired
    public void userService(UserService userService) {
        WebSocket.userService = userService;
    }
}
