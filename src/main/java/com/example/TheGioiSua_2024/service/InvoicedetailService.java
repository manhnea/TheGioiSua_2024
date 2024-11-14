package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDetailDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoicedetailService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<?> saveInvoicedetail(Invoicedetail invoicedetail) {
        try {
            // Tìm Milkdetail theo ID
            Milkdetail milkdetail = milkdetailRepository
                    .findById(invoicedetail.getMilkDetail().getId())
                    .orElseThrow(() -> new RuntimeException("Không Tồn Tại"));
            // Kiểm tra số lượng tồn kho
            if (invoicedetail.getQuantity() > milkdetail.getStockquantity()) {
                Invoice invoice = invoiceRepository.findById(invoicedetail.getInvoice().getId())
                        .orElseThrow(() -> new RuntimeException("Hoá Đơn Không Tồn Tại"));
                invoiceRepository.delete(invoice);
                return ResponseEntity.badRequest().body(Map.of("error", "Số Lượng Không Phù Hợp\n Số Lượng Còn Lại:" + milkdetail.getStockquantity()));
            }
            // Cập nhật số lượng tồn kho
            milkdetail.setStockquantity(milkdetail.getStockquantity() - invoicedetail.getQuantity());
            // Lưu lại Milkdetail   
            milkdetailRepository.save(milkdetail);
            // Đặt trạng thái cho Invoicedetail
            invoicedetail.setStatus(Status.Active);
            // Lưu lại Invoicedetail
            invoicedetailRepository.save(invoicedetail);
            // Trả về phản hồi thành công
            return ResponseEntity.ok(Map.of("message", "Thêm Thành Công"));
        } catch (RuntimeException e) {
            // Trả về phản hồi lỗi
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
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
        existingInvoicedetail.setStatus(Status.Active);
        return "Cập nhật chi tiết hóa đơn thành công!";
    }

    @Override
    public String deleteInvoicedetail(Long id) {
        Invoicedetail existingInvoicedetail = invoicedetailRepository.findById(id).orElseThrow();
        if (existingInvoicedetail.getStatus() == Status.Delete) {
            existingInvoicedetail.setStatus(Status.Active);
            invoicedetailRepository.save(existingInvoicedetail);
            return "Chi tiết hóa đơn đã bị xóa!";
        } else {
            existingInvoicedetail.setStatus(Status.Delete);
            invoicedetailRepository.save(existingInvoicedetail);
            return "Xóa chi tiết hóa đơn thành công!";
        }
    }

    public Invoicedetail getInvoicedetailById(Long id) {
        return invoicedetailRepository.findById(id).orElseThrow();
    }

    @Override
    public List<InvoiceDetailDto> findInvoiceDetailsByInvoiceId(Long invoiceId) {
        return invoicedetailRepository.findInvoiceDetailsByInvoiceId(invoiceId);
    }
}
