package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.TransactionHistory;
import com.example.TheGioiSua_2024.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/historyBank")
    public ResponseEntity<JsonNode> callMbBankApi() {
        try {
            JsonNode response = apiService.callMbBankApi();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @PostMapping("/transactionHistory")
    public ResponseEntity<String> checkTransactionData(@RequestBody TransactionHistory request) {
        String result = apiService.checkTransactionData(request);

        if ("Thành công".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }



}
