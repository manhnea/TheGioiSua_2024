package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Invoicedetail;

import java.util.List;

public interface IInvoicedetailService {
    List<Invoicedetail> getInvoicedetailList();

    String saveInvoicedetail(Invoicedetail invoicedetail);

    String updateInvoicedetail(Long id, Invoicedetail invoicedetail);

    void deleteInvoicedetail(Long id, Invoicedetail invoicedetail);

    Invoicedetail getInvoicedetailById(Long id);
}
