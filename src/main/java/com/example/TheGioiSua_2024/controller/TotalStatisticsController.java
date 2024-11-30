package com.example.TheGioiSua_2024.controller;


import com.example.TheGioiSua_2024.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Thongke")
public class TotalStatisticsController {

  @Autowired
  private InvoiceService invoiceService;


}
