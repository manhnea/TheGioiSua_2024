package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.repository.UserinvoiceRepository;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoiceService;
import com.example.TheGioiSua_2024.util.Status;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

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
  @Autowired
  private UserinvoiceRepository userinvoiceRepository;

  @Transactional
  public List<Invoice> getInvoiceList() {
    return invoiceRepository.findAll();
  }

  @Override
  public ResponseEntity<?> saveInvoice(InvoiceDto invoiceDto) {
    Voucher voucher = null;
    List<Invoicedetail> invoicedetails = invoiceDto.getInvoiceDetails();
    Userinvoice byller = new Userinvoice();
    Userinvoice seller = new Userinvoice();
    Invoice invoice = new Invoice();
    if (invoicedetails.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("error", "Vui Lòng Thêm Sản Phẩm"));
    }
    invoice.setInvoicecode(invoiceDto.getInvoiceCode());
    invoice.setPhonenumber(invoiceDto.getPhonenumber());
    invoice.setDeliveryaddress(invoiceDto.getDeliveryaddress());
    invoice.setPaymentmethod(invoiceDto.getPaymentmethod());
    invoice.setDiscountamount(invoiceDto.getSotienGiamGia());
    invoice.setTotalamount(invoiceDto.getTongTien());
    if (invoiceDto.getVoucherCode() != null) {
      voucher = voucherRepository.vouchercode(invoiceDto.getVoucherCode());
      if (voucherRepository.existsUserInvoiceByUserAndVoucher(invoiceDto.getNguoiTao().getId(),
        voucher.getVouchercode())) {
        return ResponseEntity.badRequest()
          .body(Map.of("error", "Tài Khoản Đã Sử Dụng Voucher Này Rồi"));
      }
      invoice.setVoucher(voucher);
      voucher.setUsagecount(voucher.getUsagecount() - 1);
      System.out.println("voucher.getDiscountpercentage(): " + voucher.getDiscountpercentage());
      voucherRepository.save(voucher);
    }
    invoice.setStatus(Status.AwaitingPayment);
    invoiceRepository.save(invoice);

    for (Invoicedetail invoicedetail : invoicedetails) {
      invoicedetail.setInvoice(invoice);
      invoicedetailRepository.save(invoicedetail);
    }

    if (invoice.getPaymentmethod().equals("NetBanking")) {
      User useller = new User();
      useller.setId(1l);
      seller.setInvoice(invoice);
      seller.setUser(useller);
      seller.setStatus(Status.Pending);
      userinvoiceRepository.save(seller);
      User ubyller = invoiceDto.getNguoiTao();
      byller.setInvoice(invoice);
      byller.setUser(ubyller);
      byller.setStatus(Status.Pending);
      userinvoiceRepository.save(byller);
    } else if (invoice.getPaymentmethod().equals("COD")) {
      invoice.setStatus(Status.ApproveOrders);
      invoiceRepository.save(invoice);
      seller.setInvoice(invoice);
      seller.setStatus(Status.Pending);
      userinvoiceRepository.save(seller);
      User ubyller = invoiceDto.getNguoiTao();
      byller.setInvoice(invoice);
      byller.setUser(ubyller);
      byller.setStatus(Status.Pending);
      userinvoiceRepository.save(byller);
    }
    System.out.println(invoice.toString());
    return ResponseEntity.ok("null");
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
      milkdetail = milkdetailRepository.findById(invoicedetail.getMilkDetail().getId())
        .orElseThrow();
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

  @Override
  public List<Object[]> getInvoices(String username, String voucherCode, LocalDateTime startDate,
    LocalDateTime endDate, Integer status, String invoiceCode) {
    return invoiceRepository.findInvoices(username, voucherCode, startDate, endDate, status,
      invoiceCode);
  }


}
