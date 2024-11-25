/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket")
public class UserWebSocket {

    // Lưu trữ danh sách các người dùng đang online
    private static final ConcurrentHashMap<String, Session> onlineUsers = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        // Khi một client kết nối, bạn có thể lưu thông tin (ví dụ: ID người dùng) vào danh sách
        String userId = session.getRequestParameterMap().get("userId").get(0); // Lấy userId từ query param
        onlineUsers.put(userId, session);
        System.out.println("User " + userId + " đã online.");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Xử lý tin nhắn từ client nếu cần
        System.out.println("Message received: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        // Khi client ngắt kết nối, xóa khỏi danh sách
        String userId = getUserIdFromSession(session);
        onlineUsers.remove(userId);
        System.out.println("User " + userId + " đã offline.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    private String getUserIdFromSession(Session session) {
        return session.getRequestParameterMap().get("userId").get(0);
    }

    // Hàm kiểm tra xem user có online không
    public static boolean isUserOnline(String userId) {
        return onlineUsers.containsKey(userId);
    }
}