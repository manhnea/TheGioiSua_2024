package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoBOT {

  @Autowired
  private UserRepository iUserRepository;

  @Autowired
  private InvoiceRepository invoiceRepository;

  private TelegramNotifier telegramNotifier = new TelegramNotifier();

  // Scheduled task to delete unverified users after 1 hour
  @Scheduled(cron = "0 0 8 * * *") // Runs at 08:00 AM every day
  public void deleteUnverifiedUsers() {
    // Get current time
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    // Use StringBuilder to accumulate messages
    StringBuilder messageBuilder = new StringBuilder();
    messageBuilder.append("Thông báo: Các người dùng đã bị xóa khỏi hệ thống do không xác minh:\n");

    // Process each user
    iUserRepository.findAll().stream()
        .filter(user -> user.getStatus() == Status.Inactive
            && currentTimestamp.getTime() - user.getRegistrationdate().getTime()
            >= 60 * 60 * 1000) // 60 phút (inactive for more than 1 hour)
        .forEach(user -> {
          // Delete the user
          iUserRepository.delete(user);

          // Append user details to the message
          messageBuilder.append("\n- Tên đăng nhập: " + user.getUsername())
              .append("\n  Email: " + user.getEmail())
              .append("\n  Họ và tên: " + user.getFullname())
              .append("\n");
        });

    // If any users were deleted, send a single notification with all the details
    if (messageBuilder.length() > 0) {
      // Send the aggregated message via Telegram
      telegramNotifier.sendUserDeletionNotification(messageBuilder.toString());

      // Optionally, you can send it to other platforms like Zalo
      telegramNotifier.sendMessageZalo(messageBuilder.toString());
    }
  }

  // Scheduled task to check for pending orders older than 24 hours
  @Scheduled(cron = "0 0 8 * * *") // Runs at 08:00 AM every day
  public void checkPendingOrders() {
    // Get current time
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    LocalDateTime currentDateTime = currentTimestamp.toLocalDateTime();

    // Aggregate all pending orders older than 24 hours into a single message
    StringBuilder messageBuilder = new StringBuilder();
    messageBuilder.append("Thông báo: Các đơn hàng chưa được giao sau 24 giờ:\n");

    invoiceRepository.findAll().stream()
        .filter(invoice -> invoice.getStatus() == 335 // Status 335: Pending or not delivered
            && Duration.between(invoice.getCreationdate(), currentDateTime).toHours()
            >= 24) // Order is older than 24 hours
        .forEach(invoice -> {
          // Append each invoice's details to the message
          messageBuilder.append("\n- Mã đơn hàng: " + invoice.getInvoicecode())
              .append("\n  Địa chỉ giao hàng: " + invoice.getDeliveryaddress())
              .append("\n  Phương thức thanh toán: " + invoice.getPaymentmethod())
              .append("\n  Số điện thoại: " + invoice.getPhonenumber())
              .append("\n");
        });

    // If there are any pending orders, send the notification
    if (messageBuilder.length() > 0) {
      // Send the aggregated message via Telegram
      telegramNotifier.sendUserDeletionNotification(messageBuilder.toString());

      // Optionally, you can send it to other platforms like Zalo
      telegramNotifier.sendMessageZalo(messageBuilder.toString());
    }
  }
}
