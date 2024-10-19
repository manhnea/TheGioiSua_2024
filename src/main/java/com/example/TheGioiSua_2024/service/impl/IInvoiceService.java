package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Invoice;

import java.util.List;

public interface IInvoiceService {

    List<Invoice> getInvoiceList();

    String saveInvoice(Invoice invoice);

    String updateInvoice(Long id, Invoice invoice);

    String deleteInvoice(Long id);

    Invoice getInvoiceById(Long id);
}
