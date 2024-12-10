/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.dto.UserOnlineDto;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.util.SessionUserLogin;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Administrator
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    InvoiceRepository invoiceRepository;

    @MessageMapping("/chat")
    public void sendMessage(@Payload String message) {
        List<UserOnlineDto> online = new ArrayList<>(SessionUserLogin.onlineUsers);
        List<UserOnlineDto> staffOnline = online.stream()
                .filter(user -> "Staff".equals(user.getRole()))
                .collect(Collectors.toList());
//        int counter = 0; // Khởi tạo bộ đếm
//        int staffCount = staffOnline.size();
//        for (UserOnlineDto userOnlineDto : staffOnline) {
//            List<InvoiceDto> invoiceList = new ArrayList<>();
//            InvoiceDto invoiceDto = invoiceRepository.f(message);
//            invoiceList.add(invoiceDto);
//            messagingTemplate.convertAndSendToUser(
//                    userOnlineDto.getUsername(), // Gửi đến username của người nhận
//                    "/queue/messages", // Đảm bảo là /user/{username}/queue/messages
//                    message
//            );
//            
//        }
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

}
