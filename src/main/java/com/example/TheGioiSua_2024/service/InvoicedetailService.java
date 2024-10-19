package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoicedetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoicedetailService implements IInvoicedetailService {
    @Autowired
    private InvoicedetailRepository invoicedetailRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MilkdetailRepository milkdetailRepository;

    @Override
    public List<Invoicedetail> getInvoicedetailList() {
        return invoicedetailRepository.findAll();
    }

    @Override
    public String saveInvoicedetail(Invoicedetail invoicedetail) {
        invoicedetail.setStatus(1);
        invoicedetailRepository.save(invoicedetail);
        return "Thêm chi tiết hóa đơn thành công";
    }

    @Override
    public String updateInvoicedetail(Long id, Invoicedetail invoicedetail) {
        Invoicedetail existingInvoicedetail = invoicedetailRepository.findById(id).orElseThrow();
        Invoice invoice = invoiceRepository.findById(existingInvoicedetail.getInvoice().getId()).orElseThrow();
        Milkdetail milkdetail = milkdetailRepository.findById(existingInvoicedetail.getMilkDetail().getId()).orElseThrow();
        existingInvoicedetail.setInvoice(invoice);
        existingInvoicedetail.setMilkDetail(milkdetail);
        existingInvoicedetail.setQuantity(invoicedetail.getQuantity());
        existingInvoicedetail.setPrice(invoicedetail.getPrice());
        existingInvoicedetail.setTotalprice(invoicedetail.getTotalprice());
        existingInvoicedetail.setStatus(1);
        return "Cập nhật chi tiết hóa đơn thành công!";
    }

    @Override
    public String deleteInvoicedetail(Long id) {
        Invoicedetail existingInvoicedetail = invoicedetailRepository.findById(id).orElseThrow();
        if(existingInvoicedetail.getStatus() == 0) {
            existingInvoicedetail.setStatus(1);
            invoicedetailRepository.save(existingInvoicedetail);
            return "Chi tiết hóa đơn đã bị xóa!";
        }else {
            existingInvoicedetail.setStatus(0);
            invoicedetailRepository.save(existingInvoicedetail);
            return "Xóa chi tiết hóa đơn thành công!";
        }
    }

    public Invoicedetail getInvoicedetailById(Long id) {
        return invoicedetailRepository.findById(id).orElseThrow();
    }
}
