package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoBOT {

  @Autowired
  private UserRepository iUserRepository;
  @Autowired
  private InvoiceRepository invoiceRepository;
  TelegramNotifier telegramNotifier = new TelegramNotifier();
  Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

  @Scheduled(fixedDelay = 600000) // Kiểm tra mỗi 10 phút (600000 ms)
  public void deleteUnverifiedUsers() {

    iUserRepository.findAll().stream()
        .filter(user -> user.getStatus() == Status.Inactive
            && currentTimestamp.getTime() - user.getRegistrationdate().getTime()
            >= 60 * 60 * 1000) // 60 phút
        .forEach(user -> {
          // Xóa người dùng
          // Xóa người dùng
          iUserRepository.delete(user);
          String message = "Thông báo: Người dùng đã bị xóa khỏi hệ thống do không xác minh." +
              "\n- Tên đăng nhập: " + user.getUsername() +
              "\n- Email: " + user.getEmail() +
              "\n- Họ và tên: " + user.getFullname();

          // Gửi thông báo tới Telegram và Zalo
          telegramNotifier.sendUserDeletionNotification(message);
          telegramNotifier.sendMessageZalo(message);
        });
  }
//  @Scheduled(fixedDelay = 600000) // Kiểm tra mỗi 10 phút (600000 ms)
//  public void deleteInvoice() {
//    invoiceRepository.findAll().stream()
//        .filter(invoice -> invoice.getStatus() == Status.Inactive
//            && currentTimestamp.getTime() - invoice.getCreationdate().get
//            >= 60 * 60 * 1000) // 60 phút
//        .forEach(user -> {
//          // Xóa người dùng
//          iUserRepository.delete(user);
//          String message = "Tài khoản :" + user.getUsername() + "chưa xác minh leen xoa";
//          // Gửi thông báo tới Telegram và Zalo
//          telegramNotifier.sendUserDeletionNotification(message);
//          telegramNotifier.sendMessageZalo(message);
//        });
//  }
}
