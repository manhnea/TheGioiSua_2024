package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDetailDto;
import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.InvoiceLog;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.InvoiceLogRepository;
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
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Autowired
    InvoiceLogRepository invoiceLogRepository;

    @Transactional
    public List<Invoice> getInvoiceList() {
        return invoiceRepository.findAll();
    }

    @Override
    public ResponseEntity<?> saveInvoice(InvoiceDto invoiceDto) {
        Voucher voucher = null;
        InvoiceLog invoiceLog = new InvoiceLog();
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
        invoice.setShippingfee(invoiceDto.getSotienShip());
        invoice.setTotalamount(invoiceDto.getTongTien());
        if (invoiceDto.getVoucherCode() != null) {
            voucher = voucherRepository.vouchercode(invoiceDto.getVoucherCode());
            if (voucher.getUsagecount() < 1) {
                return ResponseEntity.badRequest().body(Map.of("error", "Voucher Đã Hết Lượt Sử Dụng"));
            }
            invoice.setVoucher(voucher);
            voucher.setUsagecount(voucher.getUsagecount() - 1);
            voucherRepository.save(voucher);
        }
        invoice.setStatus(Status.ApproveOrders);
        invoiceLog.setStatus(Status.ApproveOrders);
        if (!invoiceDto.getPaymentmethod().equals("COD")) {
            invoice.setStatus(Status.UnPaid);
            invoiceLog.setStatus(Status.UnPaid);
        }
        invoiceRepository.save(invoice);
        invoiceLog.setInvoice(invoice);
        invoiceLogRepository.save(invoiceLog);
        for (Invoicedetail invoicedetail : invoicedetails) {
            invoicedetail.setInvoice(invoice);
            invoicedetailRepository.save(invoicedetail);
        }
        telegramNotifier.sendMessageZalo(
                "Mã Hóa Đơn: " + invoice.getInvoicecode() + "\n" + "http://160.30.21.47:3004/invicedetail/" + invoice.getInvoicecode() + "\n" +"Số Điện Thoại: "
                + invoice.getPhonenumber() + "\n" + "Địa Chỉ Giao Hàng: " + invoice.getDeliveryaddress()
                + "\n" + "Tổng Tiền: " + invoice.getTotalamount() + "\n" + "Phương Thức Thanh Toán: "
                + invoice.getPaymentmethod());
        byller.setInvoice(invoice);
        byller.setUser(nguoiMua);
        byller.setStatus(Status.Active);
        userinvoiceRepository.save(byller);
        if (!invoiceDto.getPaymentmethod().equals("COD")) {
            User admin = new User();
            admin.setId(1l);
            seller.setInvoice(invoice);
            seller.setUser(admin);
            seller.setStatus(Status.Active);
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
        return invoiceRepository.findInvoicesByBuyerId(id);
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
        InvoiceLog invoiceLog = new InvoiceLog();
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
        invoiceLog.setInvoice(invoice);
        invoiceLog.setStatus(Status.SuccessfulPayment);
        return true;
    }

    @Override
    public boolean cancelInvoice(Long id) {
        try {
            Invoice invoice = invoiceRepository.findById(id).orElseThrow();
            invoice.setStatus(Status.Canceled);
            invoiceRepository.save(invoice);
            InvoiceLog invoiceLog = new InvoiceLog();
            invoiceLog.setInvoice(invoice);
            invoiceLog.setDescription("Hoá Đơn Huỷ Do Hết Sản Phẩm");
            invoiceLog.setStatus(Status.Canceled);
            invoiceLogRepository.save(invoiceLog);
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
    public String updatequantity(Long id, Invoice invoice) {
        // Find the existing invoice
        Invoice existingInvoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Check if the invoice has a voucher
        if (existingInvoice.getVoucher() != null && existingInvoice.getVoucher().getId() != null) {
            Voucher voucher = voucherRepository.findById(existingInvoice.getVoucher().getId()).orElseThrow();
            if (existingInvoice.getTotalamount() >= voucher.getMinamount()) {
                int discountAmount = invoice.getTotalamount() * voucher.getDiscountpercentage() / 100;
                if (discountAmount > voucher.getMaxamount()) {
                    discountAmount = (int) voucher.getMaxamount();
                }

                // Calculate new total after applying discount
                int total = invoice.getTotalamount() - discountAmount;

                // Update discount amount and total amount in the invoice
                existingInvoice.setDiscountamount(discountAmount);
                existingInvoice.setTotalamount(total);
            }
        } else {
            // If there is no voucher, set discount amount to 0 and use the provided total amount
            existingInvoice.setDiscountamount(0);
            existingInvoice.setTotalamount(invoice.getTotalamount());
        }

        // Save the updated invoice (only once at the end)
        invoiceRepository.save(existingInvoice);

        // Return success message
        return "Cập nhật hóa đơn thành công!";
    }

    @Override
    public Page<InvoiceDto> getInvoicespage(Long buyerId, LocalDateTime startDate, LocalDateTime endDate, Integer trangThai, Pageable pageable) {
        return invoiceRepository.findInvoicesByBuyerIds(buyerId, trangThai, startDate, endDate, pageable);
    }

    @Override
    public ResponseEntity<?> waitingInvoice(Long id, Long usellerid) {
        User user = userRepository.findById(usellerid).get();
        List<Invoicedetail> invoicedetails = invoicedetailRepository.invoicedetails(id);
        InvoiceLog invoiceLog = new InvoiceLog();
        Userinvoice userinvoice = new Userinvoice();
        Invoice invoice = invoiceRepository.findById(id).get();
        if (invoice == null || invoice.getStatus() != Status.ApproveOrders) {
            return ResponseEntity.badRequest().body(Map.of("error", "Hoá đơn không tồn tại"));
        }
        for (Invoicedetail invoicedetail : invoicedetails) {
            Milkdetail milkdetail = milkdetailRepository.findById(invoicedetail.getMilkDetail().getId()).get();
            if(invoicedetail.getQuantity()>milkdetail.getStockquantity()){
                return ResponseEntity.badRequest().body(Map.of("error", milkdetail.getMilkdetailcode()+"Số lượng không đủ"));
            }
        }
        for (Invoicedetail invoicedetail : invoicedetails) {
            Milkdetail milkdetail = milkdetailRepository.findById(invoicedetail.getMilkDetail().getId()).get();
            milkdetail.setStockquantity(milkdetail.getStockquantity()-invoicedetail.getQuantity());
            milkdetailRepository.save(milkdetail);
        }
        invoice.setStatus(Status.Waiting);
        invoiceRepository.save(invoice);
        invoiceLog.setInvoice(invoice);
        invoiceLog.setStatus(Status.Waiting);
        invoiceLogRepository.save(invoiceLog);
        userinvoice.setInvoice(invoice);
        userinvoice.setUser(user);
        userinvoice.setStatus(Status.Active);
        userinvoiceRepository.save(userinvoice);
        return ResponseEntity.ok(Map.of("message", "Cập nhật thành công"));
    }

    @Override
    public ResponseEntity<?> findInvoicesByInvoiceCode(String invoiceCode) {
        return ResponseEntity.ok(Map.of("message", invoiceRepository.findInvoicesByInvoiceCode(invoiceCode)));
    }

    @Scheduled(fixedRate = 60000)
    @Override
    public void cancelUnPaidOrders() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(15); // Đơn quá 15 phút
        Pageable pageRequest = PageRequest.of(0, 100); // Phân trang: 100 bản ghi mỗi lần

        while (true) {
            // Lấy danh sách đơn hàng Pending trước thời gian cutoff
            List<Invoice> orders = invoiceRepository.findUnPaidInvoicesBefore(cutoffTime, pageRequest);
            if (orders.isEmpty()) {
                break;
            }

            // Đổi trạng thái các đơn hàng thành "Cancelled" và ghi log
            List<InvoiceLog> logs = new ArrayList<>();
            orders.forEach(order -> {
                order.setStatus(Status.Canceled);

                // Tạo log cho hóa đơn
                InvoiceLog log = new InvoiceLog();
                log.setInvoice(order);
                log.setCreated_at(LocalDateTime.now());
                log.setDescription("Hoá đơn Huỷ Do Hết Giờ");
                log.setStatus(Status.Canceled);
                logs.add(log);
            });

            // Cập nhật lại cơ sở dữ liệu
            invoiceRepository.saveAll(orders);
            invoiceLogRepository.saveAll(logs);

            // Chuyển sang trang tiếp theo
            pageRequest = pageRequest.next();
        }
    }

    @Override
    public ResponseEntity<?> updateInvoice(Long id, InvoiceDto invoiceDto) {
        // Tìm invoice theo ID, nếu không tìm thấy trả về null
        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice == null) {
            // Log thông tin nếu không tìm thấy Invoice
            System.out.println("Không tìm thấy hóa đơn với ID: " + id);
            return ResponseEntity.badRequest().body(Map.of("error", "Không tìm thấy invoice với ID: " + id));
        }

        // Cập nhật thông tin hóa đơn
        System.out.println("Cập nhật thông tin hóa đơn với ID: " + id);
        invoice.setDeliveryaddress(invoiceDto.getDeliveryaddress());
        invoice.setFullname(invoiceDto.getNguoiNhanHang());
        invoice.setPhonenumber(invoiceDto.getPhonenumber());
        invoice.setShippingfee(invoiceDto.getSotienShip());
        invoice.setTotalamount(invoiceDto.getTongTien());
        invoiceRepository.save(invoice);

        // Lấy danh sách invoicedetail từ DTO và xóa các invoicedetail cũ
        List<Invoicedetail> invoicedetails = invoiceDto.getInvoiceDetails();
        List<Invoicedetail> invoicedetailByIDInvoice = invoicedetailRepository.invoicedetails(id);
        // Xóa tất cả invoicedetails cũ trong cơ sở dữ liệu
        invoicedetailRepository.deleteAll(invoicedetailByIDInvoice);
        System.out.println("Đã xóa tất cả InvoiceDetails cũ");
        // Thêm mới các invoicedetail từ DTO
        System.out.println("Thêm mới các InvoiceDetails:");
        for (Invoicedetail invoicedetail : invoicedetails) {
            // Tính lại totalPrice cho mỗi invoicedetail
            invoicedetail.setTotalprice(invoicedetail.getQuantity() * invoicedetail.getPrice());
            invoicedetail.setInvoice(invoice);  // Gán invoice cho invoiceDetail
            invoicedetailRepository.save(invoicedetail);
            System.out.println("Đã lưu InvoiceDetail: MilkDetail ID = " + invoicedetail.getMilkDetail().getId());
        }
        return ResponseEntity.ok(Map.of("message", "Cập nhật hoá đơn thành công"));
    }

}
