package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.InvoiceDetailAdminDTO;
import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.entity.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IInvoiceService {

    List<Invoice> getInvoiceList();

    ResponseEntity<?> saveInvoice(InvoiceDto invoiceDto);
    
    ResponseEntity<?> findInvoicesByInvoiceCode(String invoiceCode);

    String updateInvoice(Long id, Invoice invoice);

    String deleteInvoice(Long id);

    Invoice getInvoiceById(Long id);

    List<InvoiceDto> getInvoices(Long id);

    long countInvoices();  // Default to current month and year

    long countInvoices(int month, int year);  // Specific month and year

    boolean paymentOK(String codeinvoice);

    boolean cancelInvoice(Long id);

    boolean waitingInvoice(Long id, Long usellerid);

    Page<Invoice> getInvoices(String paymentmethod, String status, String invoiceCode,
            String phonenumber,
            String deliveryAddress,
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    String updatequantity(Long id, @Valid Invoice invoice);
}
