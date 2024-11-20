package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoiceService;
import com.example.TheGioiSua_2024.util.Status;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
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
    @Autowired
    private InvoicedetailRepository invoicedetailRepository;
    @Autowired
    private MilkdetailRepository milkdetailRepository;

    @Transactional
    public List<Invoice> getInvoiceList() {
        return invoiceRepository.findAll();
    }

    @Override
    public Long saveInvoice(@RequestBody Invoice invoice) {
//        Integer maxId = invoiceRepository.findMaxId();
//        if (maxId == null) {
//            maxId = 1;  // Nếu bảng trống thì bắt đầu từ 1
//        } else {
//            maxId++;
//        }
//        // Tạo mã chi tiết sản phẩm theo định dạng "MD" + 3 số
//        String invoiceCode = String.format("HD%03d", maxId);
//        invoice.setInvoicecode(invoiceCode);
        Voucher voucher = null;
        if (invoice.getVoucher() != null) {
            voucher = voucherRepository.findById(invoice.getVoucher().getId()).orElseThrow();
            voucher.setUsagecount(voucher.getUsagecount()- 1);
            System.out.println("voucher.getDiscountpercentage(): " + voucher.getDiscountpercentage());
            voucherRepository.save(voucher);
        }
        invoice.setStatus(Status.AwaitingPayment);
        invoiceRepository.save(invoice);
        return invoice.getId();
    }

    @Override
    public String updateInvoice(Long id, Invoice invoice) {
        // Lấy hóa đơn hiện tại từ cơ sở dữ liệu
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow();

        // Kiểm tra và cập nhật từng trường nếu không phải null
        existingInvoice.setPhonenumber(invoice.getPhonenumber() != null ? invoice.getPhonenumber()
                : existingInvoice.getPhonenumber());
        existingInvoice.setDeliveryaddress(
                invoice.getDeliveryaddress() != null ? invoice.getDeliveryaddress()
                : existingInvoice.getDeliveryaddress());
        existingInvoice.setStatus(
                invoice.getStatus() != 0 ? invoice.getStatus() : existingInvoice.getStatus());

        // Lưu lại hóa đơn đã cập nhật
        invoiceRepository.save(existingInvoice);

        return "Cập nhật hóa đơn thành công!";
    }

    @Override
    public String deleteInvoice(Long id) {
        Invoice invoice1 = invoiceRepository.findById(id).orElseThrow();
        if (invoice1.getStatus() == Status.Delete) {
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

    @Override
    public List<InvoiceDto> getInvoices(Long id) {
        return invoiceRepository.findInvoices(id);
    }

    @Override
    public long countInvoices() {
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        return invoiceRepository.countInvoices(currentMonth, currentYear);
    }

    @Override
    public long countInvoices(int month, int year) {
        return invoiceRepository.countInvoices(month, year);
    }

    @Override
    public boolean paymentOK(String codeinvoice) {
        Invoice invoice = null;
        List<Invoicedetail> invoicedetails = null;
        Milkdetail milkdetail = null;
//
        invoice = invoiceRepository.findbycode(codeinvoice);
        if (invoice == null) {
            return false;
        }
        invoicedetails = invoicedetailRepository.invoicedetails(invoice.getId());
        for (Invoicedetail invoicedetail : invoicedetails) {
            milkdetail = milkdetailRepository.findById(invoicedetail.getMilkDetail().getId()).orElseThrow();
            System.out.println("firt:milkdetail.getStockquantity(): " + milkdetail.getStockquantity());
            milkdetail.setStockquantity(milkdetail.getStockquantity() - invoicedetail.getQuantity());
            System.out.println("last:milkdetail.getStockquantity(): " + milkdetail.getStockquantity());
            milkdetailRepository.save(milkdetail);
        }
        return true;
    }
    @Override
    public boolean cancelInvoice(Long id) {
        try {
            Invoice invoice = invoiceRepository.findById(id).orElseThrow();
            invoice.setStatus(Status.Canceled);
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
