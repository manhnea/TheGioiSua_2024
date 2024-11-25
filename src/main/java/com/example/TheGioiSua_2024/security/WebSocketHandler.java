/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.security;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.CloseStatus;

public class WebSocketHandler extends TextWebSocketHandler {

    // Lưu trữ các session của người dùng online
    private static final ConcurrentHashMap<String, WebSocketSession> onlineUsers = new ConcurrentHashMap<>();

    // Khi một client kết nối
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId"); // Giả sử bạn lưu userId trong session attributes
        onlineUsers.put(userId, session); // Thêm userId và session vào map
        System.out.println("User " + userId + " connected.");
    }

    // Khi một client gửi tin nhắn
    protected void handleTextMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Xử lý tin nhắn nếu cần
    }

    // Khi một client ngắt kết nối
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("userId"); // Giả sử bạn lưu userId trong session attributes
        onlineUsers.remove(userId); // Xóa người dùng khỏi danh sách online
        System.out.println("User " + userId + " disconnected.");
    }

    // Hàm kiểm tra ai đang online
    public static boolean isUserOnline(String userId) {
        return onlineUsers.containsKey(userId);
    }

    // Hàm lấy danh sách người dùng đang online
    public static ConcurrentHashMap<String, WebSocketSession> getOnlineUsers() {
        return onlineUsers;
    }
}
