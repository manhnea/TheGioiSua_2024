package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.VoucherDto;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IVoucherService;
import com.example.TheGioiSua_2024.util.Status;
import jakarta.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@Service
public class VoucherService implements IVoucherService {

  @Autowired
  private VoucherRepository voucherRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;

  @Override
  public List<Voucher> getVoucherList() {
    return voucherRepository.findAll();
  }

  @Override
  public String saveVoucher(String token, Voucher voucher) {
    // Loại bỏ khoảng trắng ở đầu và cuối mã voucher
    String trimmedName = voucher.getVouchercode().trim();
    voucher.setVouchercode(trimmedName);
    LocalDate currentDate = LocalDate.now();  // Lấy ngày hiện tại
    if (currentDate.isBefore(voucher.getStartdate())) {
      return "Ngày Bắt Đầu Phải Lớn Hơn Hoặc Bằng Ngày Hiện Tại";
    }
    // Kiểm tra xem mã voucher đã tồn tại chưa
    Optional<Voucher> existingVoucher = getVoucherByName(trimmedName);
    if (existingVoucher.isPresent()) {
      return "Voucher với mã này đã tồn tại.";
    }
    voucher.setStatus(Status.Active); // Kích hoạt voucher
    String username = jwtUtilities.extractUsername(token);

    String message = String.format(
        "Mã Voucher: %s, Phần trăm giảm giá: %s, Ngày bắt đầu: %s, Ngày kết thúc: %s, Số tiền giảm tối đa: %s, Số lần sử dụng: %s",
        voucher.getVouchercode(),
        voucher.getDiscountpercentage(),
        voucher.getStartdate(),
        voucher.getEnddate(),
        voucher.getMaxamount(),
        voucher.getUsagecount()
    );
    Log log = new Log(); // Tạo log
    log.setAction("Thêm voucher");
    log.setDescription(message);
    logService.saveLog(username, log);
    voucherRepository.save(voucher);
    return "Đã lưu voucher thành công.";
  }

  @Override
  public String updateVoucher(String token, Long id, Voucher voucher) {
    Voucher existingVoucher = voucherRepository.findById(id).orElseThrow();
    String currentVoucherCode = existingVoucher.getVouchercode();
    String messagecu = String.format(
        "Mã Voucher: %s, Phần trăm giảm giá: %s, Ngày bắt đầu: %s, Ngày kết thúc: %s, Số tiền giảm tối đa: %s, Số lần sử dụng: %s",
        existingVoucher.getVouchercode(),
        existingVoucher.getDiscountpercentage(),
        existingVoucher.getStartdate(),
        existingVoucher.getEnddate(),
        existingVoucher.getMaxamount(),
        existingVoucher.getUsagecount()
    );
    String message = String.format(
        "Mã Voucher: %s, Phần trăm giảm giá: %s, Ngày bắt đầu: %s, Ngày kết thúc: %s, Số tiền giảm tối đa: %s, Số lần sử dụng: %s",
        voucher.getVouchercode(),
        voucher.getDiscountpercentage(),
        voucher.getStartdate(),
        voucher.getEnddate(),
        voucher.getMaxamount(),
        voucher.getUsagecount()
    );
    String username = jwtUtilities.extractUsername(token);
    // Nếu mã voucher không thay đổi
    if (currentVoucherCode.equals(voucher.getVouchercode())) {
      existingVoucher.setDiscountpercentage(voucher.getDiscountpercentage());
      existingVoucher.setMaxamount(voucher.getMaxamount());
      existingVoucher.setEnddate(voucher.getEnddate());
      existingVoucher.setUsagecount(voucher.getUsagecount());
      existingVoucher.setStartdate(voucher.getStartdate());
      Log log = new Log(); // Tạo log
      log.setAction("Cập nhật voucher");
      log.setDescription(String.format("%s được sửa thành %s", messagecu, message));
      logService.saveLog(username, log);
      voucherRepository.save(existingVoucher);

      return "Đã cập nhật voucher thành công.";
    } // Nếu mã voucher thay đổi, kiểm tra xem mã mới đã tồn tại chưa
    else if (voucherRepository.findByVoucher(voucher.getVouchercode()).isPresent()) {
      return "Mã voucher này đã tồn tại.";
    }

    existingVoucher.setDiscountpercentage(voucher.getDiscountpercentage());
    existingVoucher.setMaxamount(voucher.getMaxamount());
    existingVoucher.setMinamount(voucher.getMinamount());
    existingVoucher.setEnddate(voucher.getEnddate());
    existingVoucher.setUsagecount(voucher.getUsagecount());
    existingVoucher.setStartdate(voucher.getStartdate());
    existingVoucher.setVouchercode(voucher.getVouchercode());
    Log log = new Log(); // Tạo log
    log.setAction("Cập nhật voucher");
    log.setDescription(String.format("%s được sửa thành %s", messagecu, message));
    logService.saveLog(username, log);
    voucherRepository.save(existingVoucher);
    return "Đã cập nhật voucher thành công.";
  }

  @Override
  public String deleteVoucher(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Voucher existingVoucher = voucherRepository.findById(id).orElseThrow();
    if (existingVoucher.getStatus() == Status.Delete) {
      existingVoucher.setStatus(Status.Active);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục voucher");
      log.setDescription(String.format(existingVoucher.getVouchercode()));
      logService.saveLog(username, log);
      voucherRepository.save(existingVoucher);
      return "Khôi phục voucher thành công.";
    } else {
      existingVoucher.setStatus(Status.Delete);
      Log log = new Log(); // Tạo log
      log.setAction("Xóa voucher");
      log.setDescription(String.format(existingVoucher.getVouchercode()));
      logService.saveLog(username, log);
      voucherRepository.save(existingVoucher);
      return "Đã xóa voucher thành công.";
    }
  }
//  
//
  @Override
  public Optional<Voucher> getVoucherByName(String voucherName) {
    return voucherRepository.findByVoucher(voucherName);
  }

  @Override
  public Voucher getVoucherById(Long id) {
    return voucherRepository.findById(id).orElseThrow();
  }

  @Override
  public ResponseEntity<?> discountmoney(String token, VoucherDto voucherDto) {
    try {
      double discountAmount = 0;
      Voucher voucher = voucherRepository.vouchercode(voucherDto.getVouchercode());

      if (voucher == null) {
        return ResponseEntity.badRequest().body(Map.of("error", "Mã Voucher Không Tồn Tại"));
      }

      // Kiểm tra usage count để đảm bảo voucher còn có thể sử dụng
      if (voucher.getUsagecount() <= 0) {
        return ResponseEntity.badRequest().body(Map.of("error", "Voucher đã hết số lần sử dụng"));
      }

      LocalDate currentDate = LocalDate.now();  // Lấy ngày hiện tại

      // Kiểm tra ngày bắt đầu và ngày kết thúc
      if (currentDate.isBefore(voucher.getStartdate()) || currentDate.isAfter(
          voucher.getEnddate())) {
        return ResponseEntity.badRequest()
            .body(Map.of("error", "Voucher đã hết hạn hoặc chưa bắt đầu"));
      }

      // Tính toán số tiền giảm giá
      discountAmount = voucherDto.getTotal() * voucher.getDiscountpercentage() / 100;
      if (discountAmount > voucher.getMaxamount()) {
        discountAmount = voucher.getMaxamount();
      }
//
//            // Giảm usage count của voucher và lưu lại
//            voucher.setUsagecount(voucher.getUsagecount() - 1);
//            voucherRepository.save(voucher);  // Lưu voucher lại
      Log log = new Log(); // Tạo log
      log.setAction("Sử dụng voucher");
      log.setDescription(String.format("Mã Voucher: %s, Số tiền giảm: %s", voucher.getVouchercode(),
          discountAmount));
      return ResponseEntity.ok(Map.of("discountAmount", discountAmount, "Vouchercode", voucher.getVouchercode()));
    } catch (PersistenceException e) {
      return ResponseEntity.badRequest().body(Map.of("error", "Database Error: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("error", "Error: " + e.getMessage()));
    }

  }

    @Override
    public ResponseEntity<?> voucherActive() {
        return ResponseEntity.ok(Map.of("Succes",  voucherRepository.voucherActive()));
   }


  @Override
  public Page<Voucher> getVoucherPage(Pageable pageable) {
    return voucherRepository.findAll(pageable);
  }

}
