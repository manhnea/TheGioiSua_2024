package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Invoice;

import java.util.List;

public interface IInvoiceService {

    List<Invoice> getInvoiceList();

    String saveInvoice(Invoice invoice);

    String updateInvoice(Long id, Invoice invoice);

    void deleteInvoice(Long id, Invoice invoice);
}
