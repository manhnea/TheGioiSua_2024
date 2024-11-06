package com.example.TheGioiSua_2024.controller;

import aj.org.objectweb.asm.TypeReference;
import com.example.TheGioiSua_2024.dto.CreatePaymentLinkRequestBody;
import com.example.TheGioiSua_2024.util.TelegramNotifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Checkout")
public class CheckoutController {

  private final PayOS payOS;

  public CheckoutController(PayOS payOS) {
    this.payOS = payOS;
  }

  @PostMapping("/create-payment-link")
  public ResponseEntity<?> createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody,
      HttpServletRequest request) {
    try {
      String baseUrl = getBaseUrl(request);
      String productName = requestBody.getProductName();
      String description = requestBody.getDescription();
      int price = requestBody.getPrice();
      String returnUrl = baseUrl + "/Checkout/success";
      String cancelUrl = baseUrl + "/Checkout/cancel";

      // Generate order code
      String orderCode = String.valueOf(
          System.currentTimeMillis() % 1000000); // Last 6 digits of timestamp

      // Store data in session directly
      request.getSession().setAttribute("productName", productName);
      request.getSession().setAttribute("price", price);
      request.getSession().setAttribute("orderCode", orderCode);

      // Create payment item and data
      ItemData item = ItemData.builder().name(productName).quantity(1).price(price).build();
      PaymentData paymentData = PaymentData.builder()
          .orderCode(Long.parseLong(orderCode))
          .amount(price)
          .description(description)
          .returnUrl(returnUrl)
          .cancelUrl(cancelUrl)
          .item(item)
          .build();

      // Generate payment link
      CheckoutResponseData data = payOS.createPaymentLink(paymentData);
      String checkoutUrl = data.getCheckoutUrl();

      // Return checkout URL as JSON response
      return ResponseEntity.ok(Map.of("checkoutUrl", checkoutUrl));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to create payment link: " + e.getMessage());
    }
  }

  @GetMapping("/success")
  public ResponseEntity<Map<String, Object>> paymentSuccess(HttpServletRequest request) {
    Map<String, Object> response = new HashMap<>();

    // Retrieve data from session
    String productName = Optional.ofNullable(
        (String) request.getSession().getAttribute("productName")).orElse("Unknown product");
    Integer price = (Integer) request.getSession().getAttribute("price");
    Long orderCode = (Long) request.getSession().getAttribute("orderCode");

    if (price != null && orderCode != null) {
      response.put("status", "success");
      response.put("message", "Payment completed successfully.");
      response.put("productName", productName);
      response.put("price", price);
      response.put("orderCode", orderCode);
      // Session data is no longer cleared here
    } else {
      response.put("status", "failed");
      response.put("message", "No transaction data found.");
    }

    return ResponseEntity.ok(response);
  }

  @GetMapping("/cancel")
  public ResponseEntity<Map<String, Object>> paymentCancel(HttpServletRequest request) {
    Map<String, Object> response = new HashMap<>();
    response.put("status", "cancelled");
    response.put("message", "Payment was cancelled.");
    response.put("orderCode", request.getSession().getAttribute("orderCode"));

    // Session data is no longer cleared here

    return ResponseEntity.ok(response);
  }

  private String getBaseUrl(HttpServletRequest request) {
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();

    StringBuilder url = new StringBuilder(scheme + "://" + serverName);
    if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https")
        && serverPort != 443)) {
      url.append(":").append(serverPort);
    }
    url.append(contextPath);
    return url.toString();
  }

  TelegramNotifier telegramNotifier = new TelegramNotifier();

  @PostMapping(path = "/confirm-webhook")
  public ResponseEntity<String> confirmWebhook(@RequestBody String requestBody) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Chuyển đổi requestBody thành JsonNode
      JsonNode jsonNode = objectMapper.readTree(requestBody);

      // Xử lý dữ liệu JSON như cần thiết
      String response =
          "Webhook received: " + jsonNode.toString(); // Ví dụ phản hồi, chỉnh sửa theo nhu cầu
      telegramNotifier.sendUserDeletionNotification(jsonNode.toString());
      telegramNotifier.sendMessageZalo(jsonNode.toString());

      // In ra dữ liệu JSON để kiểm tra
      System.out.println(jsonNode);

      // Trả về phản hồi đã được đóng gói trong ResponseEntity
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Invalid JSON format: " + e.getMessage());
    }

  }
}
