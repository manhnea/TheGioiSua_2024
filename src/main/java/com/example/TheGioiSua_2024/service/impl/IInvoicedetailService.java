package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.InvoiceDetailDto;
import com.example.TheGioiSua_2024.entity.Invoicedetail;

import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IInvoicedetailService {
    List<Invoicedetail> getInvoicedetailList();

    ResponseEntity<?> saveInvoicedetail(Invoicedetail invoicedetail);

    String updateInvoicedetail(Long id, Invoicedetail invoicedetail);

    String  deleteInvoicedetail(Long id);

    Invoicedetail getInvoicedetailById(Long id);
    
    List<InvoiceDetailDto> findInvoiceDetailsByInvoiceId( Long invoiceId);
}
