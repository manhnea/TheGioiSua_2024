package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.repository.PackagingunitRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IPackagingunitService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackagingunitService implements IPackagingunitService {

  @Autowired
  private PackagingunitRepository packagingunitRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;

  @Override
  public String addPackagingunit(String token, Packagingunit packagingunit) {
    // Loại bỏ khoảng trắng ở đầu và cuối tên
    String trimmedName = packagingunit.getPackagingunitname().trim();
    packagingunit.setPackagingunitname(trimmedName);
    // Kiểm tra xem tên đã tồn tại chưa
    Optional<Packagingunit> existingContainer = getPackagingunitByName(trimmedName);
    if (existingContainer.isPresent()) {
      return "Đơn vị đóng gói với tên này đã tồn tại.";
    }
    packagingunit.setStatus(Status.Active);
    String username = jwtUtilities.extractUsername(token);
    Log log = new Log();
    log.setAction("Thêm đơn vị đóng gói");
    log.setDescription("đơn vị đóng gói %s" + packagingunit.getPackagingunitname());
    logService.saveLog(username, log);
    packagingunitRepository.save(packagingunit);
    return "Thêm đơn vị đóng gói thành công.";
  }

  @Override
  public List<Packagingunit> getAllPackagingunit() {
    return packagingunitRepository.findAll();
  }

  @Override
  public String updatePackagingunit(String token, Long id, Packagingunit packagingunit) {
    // Loại bỏ khoảng trắng ở đầu và cuối tên
    String username = jwtUtilities.extractUsername(token);
    String trimmedName = packagingunit.getPackagingunitname().trim();
    packagingunit.setPackagingunitname(trimmedName);
    // Kiểm tra xem tên đã tồn tại chưa
    Optional<Packagingunit> existingContainer = getPackagingunitByName(trimmedName);
    if (existingContainer.isPresent()) {
      return "Tên đơn vị đóng gói này đã tồn tại.";
    }
    Packagingunit existingPackagingunit = packagingunitRepository.findById(id).orElseThrow();
    String oldpackagingunitName = existingPackagingunit.getPackagingunitname();
    String newpackagingunitName = packagingunit.getPackagingunitname();
    Log log = new Log();
    log.setAction("Cập nhật đơn vị đóng gói");
    log.setDescription(
      String.format("đơn vị đóng gói %s thành %s", oldpackagingunitName, newpackagingunitName));
    logService.saveLog(username, log);
    existingPackagingunit.setPackagingunitname(packagingunit.getPackagingunitname());
    packagingunitRepository.save(existingPackagingunit);
    return "Cập nhật đơn vị đóng gói thành công.";
  }

  @Override
  public String deletePackagingunit(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Packagingunit existingPackagingunit = packagingunitRepository.findById(id).orElseThrow();
    Log log = new Log();

    if (existingPackagingunit.getStatus() == Status.Delete) {
      existingPackagingunit.setStatus(Status.Active);
      log.setAction("Khôi phục đơn vị đóng gói");
      log.setDescription(
        String.format("đơn vị đóng gói %s", existingPackagingunit.getPackagingunitname()));
      packagingunitRepository.save(existingPackagingunit);
      return "Khôi phục đơn vị đóng gói thành công.";
    } else {
      existingPackagingunit.setStatus(Status.Delete);
      log.setAction("Xóa đơn vị đóng gói");
      log.setDescription(
        String.format("đơn vị đóng gói %s", existingPackagingunit.getPackagingunitname()));
      packagingunitRepository.save(existingPackagingunit);
      return "Xóa đơn vị đóng gói thành công.";
    }
  }

  @Override
  public Optional<Packagingunit> getPackagingunitByName(String packagingunitName) {
    return packagingunitRepository.findByPackagingunitname(
      packagingunitName); // Đã sửa để trả về đối tượng thực tế
  }

  @Override
  public Packagingunit getPackagingunitById(Long id) {
    return packagingunitRepository.findById(id).orElseThrow();
  }
}
