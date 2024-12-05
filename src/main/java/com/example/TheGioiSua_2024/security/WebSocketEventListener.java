/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.security;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private WebSocketAuthInterceptor webSocketAuthInterceptor;

    @EventListener
public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    System.out.println("Headers: " + headerAccessor.getMessageHeaders());

    // Truy xuất thông tin session từ simpConnectMessage
    Object simpConnectMessage = headerAccessor.getHeader("simpConnectMessage");
    
    if (simpConnectMessage instanceof GenericMessage<?>) {
        GenericMessage<?> connectMessage = (GenericMessage<?>) simpConnectMessage;
        
        // Lấy nativeHeaders
        Map<String, Object> nativeHeaders = (Map<String, Object>) connectMessage.getHeaders().get("nativeHeaders");

        if (nativeHeaders != null) {
            // Lấy simpSessionAttributes từ nativeHeaders
            Map<String, Object> simpSessionAttributes = (Map<String, Object>) nativeHeaders.get("simpSessionAttributes");
            
            if (simpSessionAttributes != null) {
                String sessionId = (String) simpSessionAttributes.get("sessionId");
                String username = (String) simpSessionAttributes.get("username");

                System.out.println("sessionId: " + sessionId);
                System.out.println("username: " + username);
            } else {
                System.out.println("simpSessionAttributes not found.");
            }
        } else {
            System.out.println("nativeHeaders not found.");
        }
    } else {
        System.out.println("simpConnectMessage not found or not of the expected type.");
    }
}


    // Lắng nghe sự kiện khi ngắt kết nối WebSocket
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectedEvent event) {
////        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
////        
////        // Lấy sessionId từ attributes khi ngắt kết nối
////        String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");
////
////        System.out.println("Disconnected sessionId: " + sessionId);
////
////        // Xóa thông tin người dùng khỏi sessionUsernameMap khi ngắt kết nối
////        if (sessionId != null) {
////            webSocketAuthInterceptor.removeUserInfoBySessionId(sessionId);
////            System.out.println("UserInfo removed for sessionId: " + sessionId);
////        }
//    }
}
