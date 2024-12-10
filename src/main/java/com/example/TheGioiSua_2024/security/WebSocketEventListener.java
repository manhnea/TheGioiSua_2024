/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.security;

import com.example.TheGioiSua_2024.dto.UserOnlineDto;
import com.example.TheGioiSua_2024.util.SessionUserLogin;
import java.util.Map;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        GenericMessage<?> connectMessage = (GenericMessage<?>) headerAccessor.getMessageHeaders().get("simpConnectMessage");

        if (connectMessage != null) {
            // Lấy simpSessionAttributes từ GenericMessage
            Map<String, Object> simpSessionAttributes = (Map<String, Object>) connectMessage.getHeaders().get("simpSessionAttributes");

            if (simpSessionAttributes != null) {
                String sessionId = (String) simpSessionAttributes.get("sessionId");
                String username = (String) simpSessionAttributes.get("username");
                if (username != null) {
                    String role = (String) simpSessionAttributes.get("role");
                    UserOnlineDto dto = new UserOnlineDto(username, role);
                    SessionUserLogin.login(dto);
                    System.out.println("User: " + username + "\nRole: " + role + "\nConnected");
                }
            } else {
                System.out.println("simpSessionAttributes not found.");
            }
        } else {
            System.out.println("simpConnectMessage not found.");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        // Trích xuất simpSessionAttributes từ header của thông điệp
        Map<String, Object> simpSessionAttributes = (Map<String, Object>) headerAccessor.getMessageHeaders().get("simpSessionAttributes");

        if (simpSessionAttributes != null) {
            String sessionId = (String) simpSessionAttributes.get("sessionId");
            String username = (String) simpSessionAttributes.get("username");
            if (username != null) {
                String role = (String) simpSessionAttributes.get("role");
                UserOnlineDto dto = new UserOnlineDto(username, role);
                SessionUserLogin.logout(dto);
                System.out.println("User: " + username + "\nRole: " + role + "\nDisconneted");
            }

        } else {
            System.out.println("simpSessionAttributes not found.");
        }
    }
}
