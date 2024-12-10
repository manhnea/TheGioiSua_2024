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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @MessageMapping("/cod")
    public void sendMessage(@Payload String message) {
        
        List<InvoiceDto> invoicecode = invoiceRepository.findInvoicesByCOD();
        List<UserOnlineDto> online = new ArrayList<>(SessionUserLogin.onlineUsers);
        List<UserOnlineDto> staffOnline = online.stream()
                .filter(user -> "Staff".equals(user.getRole()))
                .collect(Collectors.toList());
        int staffCount = staffOnline.size();
        int invoiceCount = invoicecode.size();

        if (staffCount > 0 && invoiceCount > 0) {
            // Chia hóa đơn cho mỗi nhân viên
            int invoicesPerStaff = invoiceCount / staffCount; // Mỗi nhân viên sẽ nhận bao nhiêu hóa đơn
            int remainder = invoiceCount % staffCount; // Hóa đơn dư nếu có

            Map<UserOnlineDto, List<InvoiceDto>> staffInvoicesMap = new HashMap<>(); // Lưu hóa đơn cho từng nhân viên

            int counter = 0;
            int currentInvoiceIndex = 0; // Dùng biến này để theo dõi vị trí hóa đơn đang được phân phối

            for (int i = 0; i < staffCount; i++) {
                // Tính số hóa đơn mà nhân viên i sẽ nhận
                int staffInvoicesCount = invoicesPerStaff + (i < remainder ? 1 : 0); // Nếu có dư thì cộng thêm 1 hóa đơn cho nhân viên đầu tiên

                // Lấy danh sách hóa đơn cho nhân viên hiện tại
                List<InvoiceDto> staffInvoices = invoicecode.subList(currentInvoiceIndex, currentInvoiceIndex + staffInvoicesCount);

                // Lưu danh sách hóa đơn vào map với key là nhân viên
                staffInvoicesMap.put(staffOnline.get(i), staffInvoices);

                // Gửi thẳng danh sách hóa đơn cho nhân viên qua WebSocket
                UserOnlineDto userOnlineDto = staffOnline.get(i);
                messagingTemplate.convertAndSendToUser(
                        userOnlineDto.getUsername(), // Gửi đến username của người nhận
                        "/queue/messages", // Đảm bảo là /user/{username}/queue/messages
                        staffInvoices // Gửi thẳng danh sách hóa đơn
                );

                // Cập nhật vị trí của hóa đơn tiếp theo
                currentInvoiceIndex += staffInvoicesCount;

                counter++; // Tăng bộ đếm
            }

            System.out.println("Đã chia và gửi hóa đơn cho " + counter + " nhân viên.");
        } else {
            System.out.println("Không có nhân viên hoặc hóa đơn để xử lý.");
        }
    }

    @MessageMapping("/invoice")
    public void invoice(@Payload String message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

}
