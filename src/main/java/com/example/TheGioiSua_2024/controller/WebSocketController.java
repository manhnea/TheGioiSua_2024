/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.UserOnlineDto;
import com.example.TheGioiSua_2024.util.SessionUserLogin;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import org.springframework.messaging.handler.annotation.Payload;

@Controller
public class WebSocketController {


  // Phương thức này được gọi khi người dùng kết nối
  @SubscribeMapping("/user/online")
  public Set<UserOnlineDto> getOnlineUsers() {
    return SessionUserLogin.onlineUsers;
  }

  // Phương thức này sẽ được gọi khi người dùng kết nối
  @MessageMapping("/connect")
  public void userConnect(@Payload UserOnlineDto userOnlineDto) {
    SessionUserLogin.onlineUsers.add(userOnlineDto);  // Thêm người dùng vào danh sách online
    System.out.println("User connected: " + userOnlineDto.getUserId());
  }

  // Phương thức này sẽ được gọi khi người dùng ngắt kết nối
  @MessageMapping("/disconnect")
  public void userDisconnect(@Payload UserOnlineDto userOnlineDto) {
    boolean remove = SessionUserLogin.onlineUsers.remove(userOnlineDto);
    System.out.println("User disconnected: " + userOnlineDto.getUserId());
    // Xóa người dùng khỏi danh sách online
  }
}
