package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.repository.UserinvoiceRepository;
import com.example.TheGioiSua_2024.service.impl.IUserinvoiceService;
import com.example.TheGioiSua_2024.util.Status;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserinvoiceService implements IUserinvoiceService {

  @Autowired
  private UserinvoiceRepository userinvoiceRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private InvoiceRepository invoiceRepository;

  @Override
  public List<Userinvoice> getUserinvoiceList() {
    return userinvoiceRepository.findAll();
  }

  @Override
  public String saveUserinvoice(Userinvoice byller) {
//    byller.setStatus(Status.Pending);
//    userinvoiceRepository.save(byller);
//    Userinvoice seller = new Userinvoice();
//    seller.setStatus(Status.Pending);
//    seller.setInvoice(byller.getInvoice());
//    User user = new User();
//    user.setId(1l);
//    seller.setUser(user);
//    userinvoiceRepository.save(seller);
    return "Đã thêm hoá đơn người dùng thành công.";
  }

  @Override
  public String updateUserinvoice(Long id, Userinvoice userinvoice) {
    Userinvoice existingUserinvoice = userinvoiceRepository.findById(id).orElseThrow();
    User user = userRepository.findById(existingUserinvoice.getUser().getId()).orElseThrow();
    Invoice invoice = invoiceRepository.findById(existingUserinvoice.getInvoice().getId())
        .orElseThrow();

    existingUserinvoice.setUser(user);
    existingUserinvoice.setInvoice(invoice);
    existingUserinvoice.setStatus(Status.Active);
    userinvoiceRepository.save(existingUserinvoice);

    return "Đã cập nhật hoá đơn người dùng thành công!";
  }

  @Override
  public void deleteUserinvoice(Long id, Userinvoice userinvoice) {
    Userinvoice existingUserinvoice = userinvoiceRepository.findById(id).orElseThrow();
    existingUserinvoice.setStatus(Status.Delete); // Ngừng hoạt động hoá đơn
    userinvoiceRepository.save(existingUserinvoice);
  }

  @Override
  public Userinvoice getUserinvoiceById(Long id) {
    return userinvoiceRepository.findById(id).orElseThrow();
  }

  @Override
  public List<Map<String, Object>> getUserInvoices() {
    List<Object[]> results = userinvoiceRepository.findUserInvoices();

    return results.stream().map(result -> {
      String username = (String) result[0];
      String invoicecode = (String) result[1];

      // Kiểm tra và ép kiểu giá trị totalAmount về Double
      Double totalAmount = (result[2] instanceof Number) ? ((Number) result[2]).doubleValue() : 0.0;

      int status = (int) result[3];

      // Xử lý trạng thái
      String statusString = switch (status) {
        case 301 -> "Chờ Duyệt Đơn";
        case 305 -> "Thanh toán thành công";
        case 336 -> "Huỷ Đơn";
        case 337 -> "Chưa Thanh Toán";
        case 338 -> "Đơn Chờ";
        case 901 -> "Chờ lấy hàng";
        case 903 -> "Đã lấy hàng";
        case 904 -> "Giao hàng";
        case 913 -> "Hoàn thành";
        default -> "Trạng thái không xác định";
      };


      // Trả về một Map để dễ dàng chuyển đổi thành JSON
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("username", username);
      resultMap.put("invoicecode", invoicecode);
      resultMap.put("totalAmount", totalAmount);
      resultMap.put("status", statusString);

      return resultMap;
    }).collect(Collectors.toList());
  }
}
