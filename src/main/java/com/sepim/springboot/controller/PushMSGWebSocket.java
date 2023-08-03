package com.sepim.springboot.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.sepim.springboot.entity.ChatMessage;
import com.sepim.springboot.service.ChatListService;
import com.sepim.springboot.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: SorrowPhage
 * @date: 2023/7/19
 *
 * 消息推送
 */
@Slf4j
@Component
@ServerEndpoint("/pushmsg/{name}")
public class PushMSGWebSocket {

    @Autowired
    public static ChatListService chatListService;

    @Autowired
    public static ChatMessageService chatMessageService;

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
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
    private static final ConcurrentHashMap<String, PushMSGWebSocket> webSocketSet = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     * session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "name") String name) {
        log.info("----------------------------------");
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[PushMsgWebSocket] 连接成功，当前连接人数为：={}", webSocketSet.size());
        log.info("----------------------------------");
        log.info("");
        log.info(name + "来了");
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this.name);
        log.info("[PushMsgWebSocket] 退出成功，当前连接人数为：={}", webSocketSet.size());

        groupSending(JSONUtils.toJSONString(name + " 走了"));
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message_str) {
        log.info("[PushMsgWebSocket] 收到消息：{}", message_str);
        ChatMessage chatMessage = JSON.parseObject(message_str, ChatMessage.class);
        boolean fromPartner = chatListService.getChatPartner(chatMessage.getFromId(), chatMessage.getToId());
        if (!fromPartner) {
            chatListService.insertChatPartner(chatMessage.getFromId(), chatMessage.getToId());
        }

        boolean toPartner = chatListService.getChatPartner(chatMessage.getToId(), chatMessage.getFromId());
        if (!toPartner) {
            chatListService.insertChatPartner(chatMessage.getToId(), chatMessage.getFromId());
        }

        JSONObject o = (JSONObject) JSONObject.toJSON(chatMessage);
        AppointSending(chatMessage.getFromId(), o.toJSONString());
        if (webSocketSet.get(chatMessage.getToId()) != null) {
            AppointSending(chatMessage.getToId(), o.toJSONString());
        }
    }

    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }


    /**
     * 群发
     *
     * @param message 发送的消息
     */
    public void groupSending(String message) {
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
     * @param name 发送目标
     * @param message 发送消息
     */
    public void AppointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
