package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.PackaginunitValidator;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.repository.PackagingunitRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IPackagingunitService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@Service
public class PackagingunitService implements IPackagingunitService {

  @Autowired
  private PackagingunitRepository packagingunitRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;


  @Override
  public List<Packagingunit> getAllPackagingunit() {
    return packagingunitRepository.findAll();
  }

  @Override
  public ResponseEntity<?> addPackagingunit(String token, Packagingunit packagingunit) {
      String error = PackaginunitValidator.validatePackagingUnit(packagingunit);
    if(error != null){
        return ResponseEntity.badRequest().body(Map.of("error",error));
    }
    if(packagingunitRepository.existsByPackagingunitname(packagingunit.getPackagingunitname())){
        return ResponseEntity.badRequest().body(Map.of("error","Đối Tượng Sử Dụng Đã Tồn Tại"));
    }
    packagingunit.setStatus(Status.Active);
    String username = jwtUtilities.extractUsername(token);
    Log log = new Log();
    log.setAction("Thêm đơn vị đóng gói");
    log.setDescription("đơn vị đóng gói %s" + packagingunit.getPackagingunitname());
    logService.saveLog(username, log);
    packagingunitRepository.save(packagingunit);
    return  ResponseEntity.ok(Map.of("succecc","Thành Công"));
  }


  @Override
  public ResponseEntity<?> updatePackagingunit(String token, Long id, Packagingunit packagingunit) {
    // Loại bỏ khoảng trắng ở đầu và cuối tên
      String error = PackaginunitValidator.validatePackagingUnit(packagingunit);
    if(error != null){
        return ResponseEntity.badRequest().body(Map.of("error",error));
    }
    if(packagingunitRepository.existsByPackagingunitname(packagingunit.getPackagingunitname())){
        return ResponseEntity.badRequest().body(Map.of("error","Đối Tượng Sử Dụng Đã Tồn Tại"));
    }
    String username = jwtUtilities.extractUsername(token);
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
    return  ResponseEntity.ok(Map.of("succecc","Thành Công"));
  }

  @Override
  public ResponseEntity<?> deletePackagingunit(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Packagingunit existingPackagingunit = packagingunitRepository.findById(id).orElseThrow();
    Log log = new Log();

    if (existingPackagingunit.getStatus() == Status.Delete) {
      existingPackagingunit.setStatus(Status.Active);
      log.setAction("Khôi phục đơn vị đóng gói");
      log.setDescription(
        String.format("đơn vị đóng gói %s", existingPackagingunit.getPackagingunitname()));
      packagingunitRepository.save(existingPackagingunit);
      return  ResponseEntity.ok(Map.of("succecc","Thành Công"));
    } else {
      existingPackagingunit.setStatus(Status.Delete);
      log.setAction("Xóa đơn vị đóng gói");
      log.setDescription(
        String.format("đơn vị đóng gói %s", existingPackagingunit.getPackagingunitname()));
      packagingunitRepository.save(existingPackagingunit);
      return  ResponseEntity.ok(Map.of("succecc","Thành Công"));
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

  @Override
  public Page<Packagingunit> getPackagingunitPage(Pageable pageable) {
    return packagingunitRepository.getPackagingunitPage(pageable);
  }

  @Override
  public Page<Packagingunit> getPackagingunitPageByName(String packagingunitName, Pageable pageable) {
    return packagingunitRepository.findByPackagingunitPage(packagingunitName, pageable);
  }
}
