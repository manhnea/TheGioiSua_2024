package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.TransactionHistory;
import com.example.TheGioiSua_2024.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/payment")
public class BankAPIController {

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
  public ResponseEntity<JsonNode> checkTransactionData(@RequestBody TransactionHistory request) {
    JsonNode result = apiService.checkTransactionData(request);
    return ResponseEntity.status(result.get("status").asInt()).body(result);
  }


}
