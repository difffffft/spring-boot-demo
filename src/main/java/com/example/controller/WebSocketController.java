package com.example.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@ServerEndpoint("/{userId}")
public class WebSocketController {
    private String userId = "";
    private Session session;
    private static final CopyOnWriteArraySet<WebSocketController> webSockets = new CopyOnWriteArraySet<>();
    private static final Map<String, Session> sessionPool = new HashMap<>();


    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        this.userId = userId;
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userId, session);
        sendAllMessage(this.userId + "已上线");
        log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:" + message);
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
    }


    // 发给所有人
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:" + message);
        for (WebSocketController webSocketController : webSockets) {
            if (webSocketController.session != null && webSocketController.session.isOpen()) {
                webSocketController.session.getAsyncRemote().sendText(message);
            }
        }
    }

    // 发给某一个人
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    // 发给某一群人
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }

}
