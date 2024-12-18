/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.InvoiceLog;
import com.example.TheGioiSua_2024.service.InvoiceLogService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hieu
 */
@RestController
@RequestMapping("/InvoiceLog")
public class InvoiceLogController {
    @Autowired
    InvoiceLogService invoiceLogService;
    @GetMapping("getById/{idinvoice}")
    public ResponseEntity<?> getInvoiceLogByIdInvoice(@PathVariable Long idinvoice) {
        return invoiceLogService.getInvoiceLogByIdInvoice(idinvoice);
    }
}
