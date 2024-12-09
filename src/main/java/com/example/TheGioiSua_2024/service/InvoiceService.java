package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.repository.RoleRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.repository.UserinvoiceRepository;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IInvoiceService;
import com.example.TheGioiSua_2024.util.EmailSend;
import com.example.TheGioiSua_2024.util.Random;
import com.example.TheGioiSua_2024.util.Status;
import com.example.TheGioiSua_2024.util.TelegramNotifier;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

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
  @Autowired
  private TelegramNotifier telegramNotifier;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  RoleRepository roleRepository;
  @Autowired
  JwtUtilities jwtUtilities;
  @Autowired
  EmailSend emailSend;

  @Transactional
  public List<Invoice> getInvoiceList() {
    return invoiceRepository.findAll();
  }

  @Override
  public ResponseEntity<?> saveInvoice(InvoiceDto invoiceDto) {
    Voucher voucher = null;
    List<Invoicedetail> invoicedetails = invoiceDto.getInvoiceDetails();
    User nguoiMua = userRepository.findByEmail(invoiceDto.getEmail());
    if (nguoiMua == null) {
      String p = Random.generateRandomPassword();
      nguoiMua = new User();
      String a[] = invoiceDto.getEmail().split("@");
      String us = a[0] + String.valueOf(Random.generateRandom4Digits());
      while (userRepository.existsByUsername(us)) {
        us = a[0] + String.valueOf(Random.generateRandom4Digits());
      }
      nguoiMua.setUsername(us);
      nguoiMua.setFullname(invoiceDto.getNguoiNhanHang());
      nguoiMua.setEmail(invoiceDto.getEmail());
      nguoiMua.setPhonenumber(invoiceDto.getPhonenumber());
      nguoiMua.setAddress(invoiceDto.getDeliveryaddress());
      nguoiMua.setRegistrationdate(new Date(System.currentTimeMillis()));
      nguoiMua.setPassword(passwordEncoder.encode(p));
      nguoiMua.setStatus(Status.Active); // Đặt trạng thái chưa xác minh
      Role role = roleRepository.findById(2L).orElseThrow(); // 2L user role
      nguoiMua.setRole(role);
      userRepository.save(nguoiMua);
      emailSend.sendAccountPasswordEmail(nguoiMua.getEmail(), nguoiMua.getUsername(), p);
    }
    Userinvoice byller = new Userinvoice();
    Userinvoice seller = new Userinvoice();
    Invoice invoice = new Invoice();
    if (invoicedetails.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("error", "Vui Lòng Thêm Sản Phẩm"));
    }
    invoice.setInvoicecode(invoiceDto.getInvoiceCode());
    invoice.setFullname(invoiceDto.getNguoiNhanHang());
    invoice.setEmail(invoiceDto.getEmail());
    invoice.setPhonenumber(invoiceDto.getPhonenumber());
    invoice.setDeliveryaddress(invoiceDto.getDeliveryaddress());
    invoice.setPaymentmethod(invoiceDto.getPaymentmethod());
    invoice.setDiscountamount(invoiceDto.getSotienGiamGia());
    invoice.setTotalamount(invoiceDto.getTongTien());
    if (invoiceDto.getVoucherCode() != null) {
      voucher = voucherRepository.vouchercode(invoiceDto.getVoucherCode());
      if (voucher.getUsagecount() < 1) {
        return ResponseEntity.badRequest().body(Map.of("error", "Voucher Đã Hết Lượt Sử Dụng"));
      }
      if (voucherRepository.existsUserInvoiceByUserAndVoucher(nguoiMua.getId(),
        voucher.getVouchercode())) {
        return ResponseEntity.badRequest()
          .body(Map.of("error", "Tài Khoản Đã Sử Dụng Voucher Này Rồi"));
      }
      invoice.setVoucher(voucher);
      voucher.setUsagecount(voucher.getUsagecount() - 1);
      voucherRepository.save(voucher);
    }
    invoice.setStatus(Status.AwaitingPayment);
    invoiceRepository.save(invoice);
    for (Invoicedetail invoicedetail : invoicedetails) {
      invoicedetail.setInvoice(invoice);
      invoicedetailRepository.save(invoicedetail);
    }
    telegramNotifier.sendMessageZalo(
      "Mã Hóa Đơn: " + invoice.getInvoicecode() + "\n" + "Số Điện Thoại: "
        + invoice.getPhonenumber() + "\n" + "Địa Chỉ Giao Hàng: " + invoice.getDeliveryaddress()
        + "\n" + "Tổng Tiền: " + invoice.getTotalamount() + "\n" + "Phương Thức Thanh Toán: "
        + invoice.getPaymentmethod());
    byller.setInvoice(invoice);
    byller.setUser(nguoiMua);
    byller.setStatus(Status.Pending);
    userinvoiceRepository.save(byller);
    if(!invoiceDto.getPaymentmethod().equals("COD")){
      User admin = new User();
      admin.setId(1l);
      seller.setInvoice(invoice);
      seller.setUser(admin);
      seller.setStatus(Status.Pending);
      userinvoiceRepository.save(seller);
    }
    return ResponseEntity.ok(
      "null");
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
  public Page<Invoice> getInvoices(String paymentmethod, String status, String invoiceCode,
    String phonenumber,
    String deliveryAddress,
    LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
    return invoiceRepository.findInvoices(paymentmethod, status, invoiceCode, phonenumber,
      deliveryAddress,
      startDate, endDate,
      pageable);
  }
  @Override
  public String updateInvoice(Long id, Invoice invoice) {
    // Lấy hóa đơn hiện tại từ cơ sở dữ liệu
    Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow();
    existingInvoice.setFullname(invoice.getFullname() != null ? invoice.getFullname()
            : existingInvoice.getFullname());
    // Kiểm tra và cập nhật từng trường nếu không phải null
    existingInvoice.setPhonenumber(invoice.getPhonenumber() != null ? invoice.getPhonenumber()
            : existingInvoice.getPhonenumber());
    existingInvoice.setDeliveryaddress(
            invoice.getDeliveryaddress() != null ? invoice.getDeliveryaddress()
                    : existingInvoice.getDeliveryaddress());
    existingInvoice.setStatus(
            invoice.getStatus() != 0 ? invoice.getStatus() : existingInvoice.getStatus());
    invoiceRepository.save(existingInvoice);
    return "Cập nhật hóa đơn thành công!";
  }



  @Override
  public String updatequantity(Long id, Invoice invoice) {
    Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
  Voucher voucher = voucherRepository.findById(existingInvoice.getVoucher().getId()).orElseThrow();
    if (invoice.getVoucher() != null && invoice.getVoucher().getVouchercode() != null) {
      voucher = voucherRepository.vouchercode(invoice.getVoucher().getVouchercode());
    }
    System.out.printf(".updatequantity(%d, %s)%n", id, invoice.getTotalamount());
    System.out.printf("voucher: %s%n", voucher);
    if (voucher != null && invoice.getTotalamount() >= voucher.getMinamount()) {
      System.out.printf(".updatequantityaaaaaaaaaaaaaaaaaaaaa(%d, %s)%n", id, invoice.getTotalamount());
      // Tính toán số tiền giảm giá theo tỷ lệ % của voucher
     int discountAmount =  invoice.getTotalamount() * voucher.getDiscountpercentage() / 100;
      if (discountAmount > voucher.getMaxamount()) {
        discountAmount = (int) voucher.getMaxamount();
      }
      int total = invoice.getTotalamount() - discountAmount;
      existingInvoice.setDiscountamount(discountAmount);
      existingInvoice.setTotalamount(total);
    } else {
      // Nếu không có voucher hoặc voucher không hợp lệ, không giảm giá
      existingInvoice.setDiscountamount(0);
      existingInvoice.setTotalamount(invoice.getTotalamount());
    }

    // Lưu hóa đơn đã được cập nhật (chỉ lưu một lần ở cuối)
    invoiceRepository.save(existingInvoice);

    // Trả về thông báo thành công
    return "Cập nhật hóa đơn thành công!";
  }

}
