/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Administrator
 */
@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sendMessage(@Payload String message, SimpMessageHeaderAccessor headerAccessor) {
        // Lấy username từ WebSocket session attributes
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        
        // Kiểm tra người gửi tin nhắn và xử lý thông điệp
        System.out.println("Message from " + username + ": " + message);
        
        // Trả lời lại message cho các client đã đăng ký
        return "Hello " + username + ", you said: " + message;
    }
}
