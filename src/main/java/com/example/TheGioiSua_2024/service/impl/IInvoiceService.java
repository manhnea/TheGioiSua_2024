package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.InvoiceDetailAdminDTO;
import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.entity.Invoice;

import java.util.List;

public interface IInvoiceService {

  List<Invoice> getInvoiceList();

  Long saveInvoice(Invoice invoice);

  String updateInvoice(Long id, Invoice invoice);

  String deleteInvoice(Long id);

  Invoice getInvoiceById(Long id);

  List<InvoiceDto> getInvoices(Long id);

  long countInvoices();  // Default to current month and year

  long countInvoices(int month, int year);  // Specific month and year
  
  
  boolean paymentOK(String codeinvoice);
}
