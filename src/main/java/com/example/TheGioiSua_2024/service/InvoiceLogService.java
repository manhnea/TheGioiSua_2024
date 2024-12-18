/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceLogDto;
import com.example.TheGioiSua_2024.entity.InvoiceLog;
import com.example.TheGioiSua_2024.repository.InvoiceLogRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoiceLogService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hieu
 */
@Service
public class InvoiceLogService implements IInvoiceLogService{
    @Autowired
    InvoiceLogRepository invoiceLogRepository;
    @Override
    public ResponseEntity<?> getInvoiceLogByIdInvoice(Long idinvoice) {
        List<InvoiceLogDto> invoiceLogs = invoiceLogRepository.getInvoiceLogByInvoiceId(idinvoice);
        if(invoiceLogs.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error","Danh Sách Log Trống"));
        }
        return ResponseEntity.ok(Map.of("message",invoiceLogs));
    }
    
}
