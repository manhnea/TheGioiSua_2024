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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Checkout")
public class CheckoutController {
    private final PayOS payOS;

    public CheckoutController(PayOS payOS) {
        this.payOS = payOS;
    }

    @PostMapping("/create-payment-link")
    public ResponseEntity<?> createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody, HttpServletRequest request) {
        try {
            final String baseUrl = getBaseUrl(request);
            final String productName = requestBody.getProductName();
            final String description = requestBody.getDescription();
            final int price = requestBody.getPrice();
            final String returnUrl = baseUrl + "/Checkout/success";
            final String cancelUrl = baseUrl + "/Checkout/cancel";

            // Generate order code
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            // Store data in session for access on success page
            request.getSession().setAttribute("productName", productName);
            request.getSession().setAttribute("price", price);
            request.getSession().setAttribute("orderCode", orderCode);

            // Create payment item and data
            ItemData item = ItemData.builder().name(productName).quantity(1).price(price).build();
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
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
            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", checkoutUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create payment link", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/success")
    public ResponseEntity<Map<String, Object>> paymentSuccess(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Retrieve data from session
        String productName = (String) request.getSession().getAttribute("productName");
        Integer price = (Integer) request.getSession().getAttribute("price");
        Long orderCode = (Long) request.getSession().getAttribute("orderCode");

        if (productName != null && price != null && orderCode != null) {
            response.put("status", "success");
            response.put("message", "Payment completed successfully.");
            response.put("productName", productName);
            response.put("price", price);
            response.put("orderCode", orderCode);

            // Clear session data
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

        // Clear session data
        request.getSession().removeAttribute("productName");
        request.getSession().removeAttribute("price");
        request.getSession().removeAttribute("orderCode");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String url = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            url += ":" + serverPort;
        }
        url += contextPath;
        return url;
    }
}
