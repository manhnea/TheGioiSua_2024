/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.InvoiceLog;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hieu
 */
public interface IInvoiceLogService {
    ResponseEntity<?> getInvoiceLogByIdInvoice(Long idinvoice);
}
