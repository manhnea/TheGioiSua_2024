package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.ShippingstatusDto;
import com.example.TheGioiSua_2024.service.impl.IShipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/webhook")
public class ShipController {

    @Autowired
    private IShipService shipService;

    @Value("${client.secret}")
    private String clientSecret;

    private boolean verifyWebhook(String data, String webhookHmac) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKey);
            byte[] hashedBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String computedHmac = Base64.getEncoder().encodeToString(hashedBytes);
            return webhookHmac.equals(computedHmac);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/listen")
    public ResponseEntity<?> listenWebhook(
            @RequestBody ShippingstatusDto shippingstatusDto,
            @RequestHeader("X-Goship-Hmac-SHA256") String webhookHmac) {

        String jsonData = convertObjectToJson(shippingstatusDto);
        boolean isVerified = verifyWebhook(jsonData, webhookHmac);

        if (isVerified) {
            return shipService.getShippingStatus(shippingstatusDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Webhook Signature.");
        }
    }

    private String convertObjectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
