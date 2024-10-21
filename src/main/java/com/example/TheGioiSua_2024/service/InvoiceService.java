package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoiceService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Invoice> getInvoiceList() {
        return invoiceRepository.findAll();
    }

    @Override
    public String saveInvoice(@RequestBody Invoice invoice) {
        String invoicecode = invoice.getInvoicecode().trim();
        invoice.setInvoicecode(invoicecode);
        if (invoiceRepository.existsByInvoicecode(invoicecode).isPresent()) {
            return "Mã hóa đơn đã tồn tại.";
        }
        invoice.setStatus(Status.Active);
        invoiceRepository.save(invoice);
        return "Thêm hóa đơn thành công!";
    }

    @Override
    public String updateInvoice(Long id, Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow();
        Voucher voucher = voucherRepository.findById(existingInvoice.getVoucher().getId()).orElseThrow();
        String invoicecode = invoice.getInvoicecode().trim();
        invoice.setInvoicecode(invoicecode);
        if (invoiceRepository.existsByInvoicecode(invoicecode).isPresent()) {
            return "Mã hóa đơn đã tồn tại.";
        }
        existingInvoice.setVoucher(voucher);
        existingInvoice.setInvoicecode(invoice.getInvoicecode());
        existingInvoice.setDiscountamount(invoice.getDiscountamount());
        existingInvoice.setTotalamount(invoice.getTotalamount());
        existingInvoice.setStatus(Status.Active);
        invoiceRepository.save(existingInvoice);
        return "Cập nhật hóa đơn thành công!";
    }

    @Override
    public String deleteInvoice(Long id) {
        Invoice invoice1 = invoiceRepository.findById(id).orElseThrow();
        if(invoice1.getStatus() == Status.Delete) {
            invoice1.setStatus(Status.Active);
            invoiceRepository.save(invoice1);
            return "Hóa đơn đã được khôi phục!";
        } else {
            invoice1.setStatus(Status.Delete);
            invoiceRepository.save(invoice1);
            return "Hóa đơn đã được xóa!";
        }
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow();
    }
}
