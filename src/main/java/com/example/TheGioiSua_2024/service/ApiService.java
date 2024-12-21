package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.TransactionHistory;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.InvoiceLog;
import com.example.TheGioiSua_2024.entity.Setting;
import com.example.TheGioiSua_2024.repository.InvoiceLogRepository;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.SettingRepository;
import com.example.TheGioiSua_2024.util.Status;
import com.example.TheGioiSua_2024.util.TelegramNotifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    private final String CODE = "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";
    private final String TOKEN = "hPuqegRLwpHBTEzfZnyoWKQxvmkdVlIGJUAYaCNscjSbFMXrDitO";
    private final String STK = "0338739954";
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceLogRepository invoiceLogRepository;
    @Autowired
    private SettingRepository settingRepository;

    public JsonNode callMbBankApi() {
        String url = "https://api.dichvudark.vn/api/ApiMbBank";
        Long id = 1L;

        // Lấy existingSetting từ repository
        Setting existingSetting = settingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setting not found"));
        System.out.println();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Code", CODE);
        headers.set("Token", existingSetting.getApikey());
        headers.set("Stk", existingSetting.getStk());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("Loai_api", "lsgdv2");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseTransactionResponse(response.getBody());
        } else {
            throw new RuntimeException("Failed to call API: " + response.getStatusCode());
        }
    }

    private JsonNode parseTransactionResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);

            JsonNode transactionHistoryList = rootNode.path("transactionHistoryList");
            for (JsonNode transaction : transactionHistoryList) {
                String description = transaction.path("description").asText();

                // Tìm và lấy phần mô tả từ chữ "demo" và 6 ký tự tiếp theo
                String modifiedDescription = description;
                Pattern pattern = Pattern.compile("HD(\\w{8})");
                Matcher matcher = pattern.matcher(description);
                if (matcher.find()) {
                    modifiedDescription = matcher.group(0); // lấy "demo" và 6 ký tự tiếp theo
                }

                ((ObjectNode) transaction).put("description", modifiedDescription);
            }
            return rootNode;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response: " + e.getMessage());
        }
    }

    public JsonNode fetchTransactionData() {
        return callMbBankApi();
    }

    public JsonNode checkTransactionData(TransactionHistory request) {
        InvoiceLog invoiceLog = new InvoiceLog();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        JsonNode transactionData = fetchTransactionData();
        TelegramNotifier telegramNotifier = new TelegramNotifier();

        for (JsonNode transaction : transactionData.path("transactionHistoryList")) {
            double creditAmount = transaction.path("creditAmount").asDouble();
            String description = transaction.path("description").asText();
            System.out.println("description:" + description);
            if (creditAmount == request.getCreditAmount() && description.equals(
                    request.getDescription())) {
                Invoice doist = invoiceRepository.findbycode(description);

                // Kiểm tra trạng thái đơn hàng
                if (doist.getStatus() == Status.UnPaid) {
                    // Cập nhật trạng thái đơn hàng và gửi thông báo nếu là AwaitingPayment
                    boolean isPayMent = invoiceService.paymentOK(description);
                    if (isPayMent) {
                        doist.setStatus(Status.SuccessfulPayment);
                        invoiceRepository.save(doist);
                        invoiceLog.setInvoice(doist);
                        invoiceLog.setStatus(Status.SuccessfulPayment);
                        invoiceLogRepository.save(invoiceLog);
                        doist.setStatus(Status.Waiting);
                        invoiceRepository.save(doist);
                        invoiceLog = new InvoiceLog();
                        invoiceLog.setInvoice(doist);
                        invoiceLog.setStatus(Status.Waiting);
                        invoiceLogRepository.save(invoiceLog);
                        try {
                            String mess = "Thông báo: Bạn có một đơn hàng mới!"
                                    + "\nMã đơn hàng: " + description
                                    + "\nSố điện thoại: " + doist.getPhonenumber()
                                    + "\nĐịa chỉ giao hàng: " + doist.getDeliveryaddress();
                            telegramNotifier.sendMessageZalo(mess);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        response.put("status", 200);
                        response.put("mess", "Thành công");
                    } else {
                        response.put("status", 400);
                        response.put("mess", "Error payment");
                    }
                } else if (doist.getStatus() == Status.SuccessfulPayment) {
                    response.put("status", 305);
                    response.put("error", "Đơn hàng này đã được thanh toán.");
                } else if (doist.getStatus() == Status.Canceled) {
                    response.put("status", 336);
                    response.put("error", "Đơn hàng này đã bị hủy.");
                }
                return response;
            }
        }
        response.put("status", 404);
        response.put("error", "Không tìm thấy giao dịch khớp");
        return response;
    }

}
