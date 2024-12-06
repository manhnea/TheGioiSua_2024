package com.example.TheGioiSua_2024.security;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

public class WebSocketHandlerImpl extends TextWebSocketHandler {

    // Khi nhận được tin nhắn
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        // Gửi phản hồi lại cho client
        session.sendMessage(new TextMessage("Hello from server"));
    }

    // Khi kết nối được thiết lập
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established: " + session.getId());
    }

    // Khi kết nối bị đóng
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + session.getId());
    }
}
