package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.CreatePaymentLinkRequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Checkout")
public class CheckoutController {

  private final PayOS payOS;
  private final String secretKey = "YOUR_SECRET_KEY"; // replace with actual secret key for HMAC validation

  public CheckoutController(PayOS payOS) {
    this.payOS = payOS;
  }

  @PostMapping("/create-payment-link")
  public ResponseEntity<?> createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody,
      HttpServletRequest request) {
    try {
      final String baseUrl = getBaseUrl(request);
      final String productName = requestBody.getProductName();
      final String description = requestBody.getDescription();
      final int price = requestBody.getPrice();
      final String returnUrl = baseUrl + "/Checkout/success";
      final String cancelUrl = baseUrl + "/Checkout/cancel";

      String currentTimeString = String.valueOf(new Date().getTime());
      long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

      request.getSession().setAttribute("productName", productName);
      request.getSession().setAttribute("price", price);
      request.getSession().setAttribute("orderCode", orderCode);

      ItemData item = ItemData.builder().name(productName).quantity(1).price(price).build();
      PaymentData paymentData = PaymentData.builder()
          .orderCode(orderCode)
          .amount(price)
          .description(description)
          .returnUrl(returnUrl)
          .cancelUrl(cancelUrl)
          .item(item)
          .build();

      CheckoutResponseData data = payOS.createPaymentLink(paymentData);
      String checkoutUrl = data.getCheckoutUrl();

      Map<String, String> response = new HashMap<>();
      response.put("checkoutUrl", checkoutUrl);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Failed to create payment link",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/success")
  public ResponseEntity<Map<String, Object>> paymentSuccess(HttpServletRequest request) {
    Map<String, Object> response = new HashMap<>();

    String productName = (String) request.getSession().getAttribute("productName");
    Integer price = (Integer) request.getSession().getAttribute("price");
    Long orderCode = (Long) request.getSession().getAttribute("orderCode");

    if (productName != null && price != null && orderCode != null) {
      response.put("status", "success");
      response.put("message", "Payment completed successfully.");
      response.put("productName", productName);
      response.put("price", price);
      response.put("orderCode", orderCode);

      request.getSession().removeAttribute("productName");
      request.getSession().removeAttribute("price");
      request.getSession().removeAttribute("orderCode");
    } else {
      response.put("status", "failed");
      response.put("message", "No transaction data found.");
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/cancel")
  public ResponseEntity<Map<String, Object>> paymentCancel(HttpServletRequest request) {
    Map<String, Object> response = new HashMap<>();
    response.put("status", "cancelled");
    response.put("message", "Payment was cancelled.");
    response.put("orderCode", request.getSession().getAttribute("orderCode"));

    request.getSession().removeAttribute("productName");
    request.getSession().removeAttribute("price");
    request.getSession().removeAttribute("orderCode");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/webhook")
  public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> payload,
      @RequestHeader("signature") String signature) {
    try {
      if (!isValidSignature(payload, signature)) {
        return new ResponseEntity<>("Invalid signature", HttpStatus.UNAUTHORIZED);
      }

      // Process the webhook payload
      System.out.println("Webhook received: " + payload);

      return new ResponseEntity<>("Webhook processed successfully", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Error processing webhook", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private boolean isValidSignature(Map<String, Object> payload, String receivedSignature) {
    try {
      String payloadJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(
          payload);

      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
      mac.init(secretKeySpec);

      byte[] rawHmac = mac.doFinal(payloadJson.getBytes());
      String calculatedSignature = Base64.getEncoder().encodeToString(rawHmac);

      return calculatedSignature.equals(receivedSignature);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private String getBaseUrl(HttpServletRequest request) {
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    String contextPath = request.getContextPath();

    String url = scheme + "://" + serverName;
    if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https")
        && serverPort != 443)) {
      url += ":" + serverPort;
    }
    url += contextPath;
    return url;
  }
}
