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
    @RequestParam(required = false) String voucher,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

    // Fetch data from service layer
    List<Object[]> revenue = totalStatisticsService.getSalesRevenue(voucher, startDate, endDate
    );

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

  @GetMapping("/by-date")
  public ResponseEntity<List<Map<String, Object>>> getRevenueByDate(
    @RequestParam(required = false) Integer voucherId,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

    // Fetch data from service layer
    List<Object[]> revenue = totalStatisticsService.getRevenueByDate(voucherId, startDate, endDate);

    // Convert Object[] to Map<String, Object> for better readability
    List<Map<String, Object>> data = new ArrayList<>();
    for (Object[] row : revenue) {
      Map<String, Object> revenueByDate = new HashMap<>();
      revenueByDate.put("date", row[0]);
      revenueByDate.put("totalRevenue", row[1]);

      data.add(revenueByDate);
    }

    // Return the processed data
    return ResponseEntity.ok(data);
  }

  @GetMapping("/by-month")
  public ResponseEntity<List<Map<String, Object>>> getRevenueByMonth() {

    // Fetch data from service layer
    List<Object[]> revenue = totalStatisticsService.getRevenueByMonth();

    // Convert Object[] to Map<String, Object> for better readability
    List<Map<String, Object>> data = new ArrayList<>();
    for (Object[] row : revenue) {
      Map<String, Object> revenueByMonth = new HashMap<>();
      revenueByMonth.put("Date", row[0]);
      revenueByMonth.put("totalRevenue", row[1]);

      data.add(revenueByMonth);
    }

    // Return the processed data
    return ResponseEntity.ok(data);
  }
}
