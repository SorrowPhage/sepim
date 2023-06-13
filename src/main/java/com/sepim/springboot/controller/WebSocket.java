package com.sepim.springboot.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.sepim.springboot.entity.ChatMessage;
import com.sepim.springboot.service.ChatMessageService;
import com.sepim.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解的作用
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class WebSocket {

    @Autowired
    public static ChatMessageService chatMessageService;

    @Autowired
    public static UserService userService;

    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     * 注意这里的kv,设计的很巧妙，v刚好是本类 WebSocket (用来存放每个客户端对应的MyWebSocket对象)
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     * session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name) {
        log.info("----------------------------------");
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}", webSocketSet.size());
        log.info("----------------------------------");
        log.info("");
        log.info(name + "来了");
        // GroupSending(JSONUtils.toJSONString(name + " 来了"));
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void OnClose() {
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}", webSocketSet.size());

        GroupSending(JSONUtils.toJSONString(name + " 走了"));
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void OnMessage(String message_str) {
        log.info("[WebSocket] 收到消息：{}", message_str);
        ChatMessage chatMessage = JSON.parseObject(message_str, ChatMessage.class);
        chatMessage.setIsLatest(1);
        if (webSocketSet.get(chatMessage.getToId()) == null) {
            log.info("-------------------该用户未连接");
            chatMessage.setType(0);
            chatMessage.setUser(userService.getById(chatMessage.getFromId()));
            chatMessageService.insertChatMessage(chatMessage);
        } else {
            chatMessage.setType(1);
            chatMessage.setUser(userService.getById(chatMessage.getFromId()));
            chatMessageService.insertChatMessage(chatMessage);
            JSONObject o = (JSONObject) JSONObject.toJSON(chatMessage);
            AppointSending(chatMessage.getToId(), o.toJSONString());
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }


    /**
     * 群发
     *
     * @param message
     */
    public void GroupSending(String message) {
        for (String name : webSocketSet.keySet()) {
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     *
     * @param name
     * @param message
     */
    public void AppointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}