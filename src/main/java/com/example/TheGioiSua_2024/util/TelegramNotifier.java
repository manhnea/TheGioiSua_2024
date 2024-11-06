package com.example.TheGioiSua_2024.util;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class TelegramNotifier {

  private final RestTemplate restTemplate;

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
    String url = "http://160.30.21.47:3002/api/sendmessage";
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("phone", "0338739954");
    requestBody.put("messageContent", messageContent);

    try {
      restTemplate.postForObject(url, requestBody, String.class);
    } catch (Exception e) {
      System.out.println("Lỗi khi gửi thông báo đến API: " + e.getMessage());
    }
  }
}
