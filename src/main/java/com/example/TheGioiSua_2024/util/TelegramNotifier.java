package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.repository.SettingRepository;
import com.example.TheGioiSua_2024.service.Settingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramNotifier {

  private final RestTemplate restTemplate;
  @Autowired
  private SettingRepository settingRepository;
  public TelegramNotifier() {
    this.restTemplate = new RestTemplate();
  }

  // Phương thức gửi thông báo đến Telegram
  public void sendUserDeletionNotification(String message) {
    Map<String, Object> body = new HashMap<>();
    body.put("chat_id", 1645636506);
    body.put("text", message);

    try {
      restTemplate.postForObject(
        "https://api.telegram.org/bot7586885101:AAEXrfXyyiDKmc0VYe6FMKQ5BK7IdAOlH9s/sendMessage",
        body,
        String.class
      );
    } catch (Exception e) {
      System.out.println("Lỗi khi gửi thông báo Telegram: " + e.getMessage());
    }
  }

  // Phương thức gửi tin nhắn đến API khác
  public void sendMessageZalo(String messageContent) {
    String url = "http://160.30.21.47:3030/api/sendmessage";
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("phone", settingRepository.findAll().get(0).getHotline());
    requestBody.put("messageContent", messageContent);

    try {
      restTemplate.postForObject(url, requestBody, String.class);
    } catch (Exception e) {
      System.out.println("Lỗi khi gửi thông báo đến API: " + e.getMessage());
    }
  }
}
