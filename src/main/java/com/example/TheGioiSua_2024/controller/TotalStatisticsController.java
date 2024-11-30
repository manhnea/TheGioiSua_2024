package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.service.impl.ITotalStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Thongke")
public class TotalStatisticsController {

  @Autowired
  private ITotalStatisticsService totalStatisticsService;

  @GetMapping("/sales-revenue")
  public ResponseEntity<List<Map<String, Object>>> getSalesRevenue(
    @RequestParam(required = false) Long voucherId,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
    @RequestParam(required = false) Integer status) {

    // Fetch data from service layer
    List<Object[]> revenue = totalStatisticsService.getSalesRevenue(voucherId, startDate, endDate,
      status);

    // Convert Object[] to Map<String, Object> for better readability
    List<Map<String, Object>> data = new ArrayList<>();
    for (Object[] row : revenue) {
      Map<String, Object> milkDetail = new HashMap<>();
      milkDetail.put("milkDetailCode", row[0]);
      milkDetail.put("totalPrice", row[1]);
      milkDetail.put("totalQuantity", row[2]);
      milkDetail.put("distinctInvoiceCount", row[3]);
      milkDetail.put("username", row[4]);

      data.add(milkDetail);
    }

    // Return the processed data
    return ResponseEntity.ok(data);
  }
}
