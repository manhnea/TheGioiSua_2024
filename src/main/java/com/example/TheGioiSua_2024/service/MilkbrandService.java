package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.MilkTypeValidator;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.repository.MilkbrandRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilkbrandService;
import com.example.TheGioiSua_2024.Validator.MilkbrandValidator;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MilkbrandService implements IMilkbrandService {

  @Autowired
  private MilkbrandRepository milkbrandRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;

  @Override
  public List<Milkbrand> getAllMilkbrands() {
    return milkbrandRepository.findAll();
  }

  @Override
  public ResponseEntity<?> addMilkbrand(String token, Milkbrand milkbrand) {
    String error = MilkbrandValidator.validateMilkbrand(milkbrand);
    if (error != null) {
      return ResponseEntity.badRequest().body(Map.of("error", error));
    }
    if (milkbrandRepository.existsByMilkbrandname(milkbrand.getMilkbrandname())) {
     return ResponseEntity.badRequest().body(Map.of("error", "Thương hiệu sữa này đã tồn tại"));
    }
    String username = jwtUtilities.extractUsername(token);
    String message = String.format(
            "Tên thương hiệu: %s, Mô tả: %s, trạng thái: %s",
            milkbrand.getMilkbrandname(),
            milkbrand.getDescription(),
            milkbrand.getStatus()
    );

    // Log the action
    Log log = new Log();
    log.setAction("Thêm thương hiệu sữa");
    log.setDescription(message);
    logService.saveLog(username, log);

    // Set the status and save milkbrand
    milkbrand.setStatus(Status.Active);  // Ensure the status is set to 'Active'
    milkbrandRepository.save(milkbrand);

    return ResponseEntity.ok(Map.of("success", "Thêm thương hiệu sữa thành công"));
  }

  @Override
  public ResponseEntity<?> updateMilkbrand(String token, Long id, Milkbrand milkbrand) {
    String error = MilkbrandValidator.validateMilkbrand(milkbrand);
    if (error != null) {
      return ResponseEntity.badRequest().body(Map.of("error", error));
    }

    String username = jwtUtilities.extractUsername(token);
    Log log = new Log(); // Tạo log
    Milkbrand existingMilkbrand = milkbrandRepository.findById(id).orElseThrow();
    String oldMilkbrandName = existingMilkbrand.getMilkbrandname();
    String newMilkbrandName = milkbrand.getMilkbrandname();
    String oldDescription = existingMilkbrand.getDescription();
    String newDescription = milkbrand.getDescription();
    String message = String.format(
      "Tên thương hiệu: %s, Mô tả: %s thành Tên thương hiệu: %s, Mô tả: %s",
      oldMilkbrandName, oldDescription, newMilkbrandName, newDescription
    );
    String currentMilkbrandName = existingMilkbrand.getMilkbrandname();
    if (currentMilkbrandName.equals(milkbrand.getMilkbrandname())) {
      existingMilkbrand.setDescription(milkbrand.getDescription());
      existingMilkbrand.setStatus(Status.Active);
      milkbrandRepository.save(existingMilkbrand);
      log.setAction("Cập nhật mô tả brand");
      log.setDescription(message);
      logService.saveLog(username, log);
      return ResponseEntity.ok(Map.of("success", "Cập nhật thương hiệu sữa thành công"));
    }
    if (milkbrandRepository.existsByMilkbrandname(milkbrand.getMilkbrandname())) {
      return ResponseEntity.badRequest().body(Map.of("error", "Thương hiệu sữa này đã tồn tại"));
    }
    existingMilkbrand.setMilkbrandname(milkbrand.getMilkbrandname());
    existingMilkbrand.setStatus(Status.Active);
    existingMilkbrand.setDescription(milkbrand.getDescription());
    milkbrandRepository.save(existingMilkbrand);
    log.setAction("Cập nhật mô tả brand");
    log.setDescription(message);
    logService.saveLog(username, log);
    return ResponseEntity.ok(Map.of("success", "Cập nhật thương hiệu sữa thành công"));
  }


  @Override
  public ResponseEntity<?> deleteMilkbrand(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Milkbrand existingMilkbrand = milkbrandRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Thương hiệu sữa không tồn tại"));

    if (existingMilkbrand.getStatus() == Status.Delete) {
      existingMilkbrand.setStatus(Status.Active);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục brand");
      log.setDescription(String.format(existingMilkbrand.getMilkbrandname()));
      logService.saveLog(username, log);
      milkbrandRepository.save(existingMilkbrand);
     return ResponseEntity.ok(Map.of("success", "Khôi phục thương hiệu sữa thành công"));
    } else {
      existingMilkbrand.setStatus(Status.Delete);
      Log log = new Log(); // Tạo log
      log.setAction("Khóa brand");
      log.setDescription(String.format(existingMilkbrand.getMilkbrandname()));
      logService.saveLog(username, log);
      milkbrandRepository.save(existingMilkbrand);
      return ResponseEntity.ok(Map.of("success", "Khóa thương hiệu sữa thành công"));
    }
  }

  @Override
  public Milkbrand getMilkbrandById(Long id) {

    return milkbrandRepository.findBydadata(id);
  }

  @Override
  public Page<Milkbrand> getMilkbrandPage(Pageable pageable) {
    return milkbrandRepository.getMilkbrandPage(pageable);
  }

  @Override
  public Page<Milkbrand> getMilkbrandsearch(String milkbrandname,String status, Pageable pageable) {
    return milkbrandRepository.findByMilkbrandnamePage(milkbrandname,status, pageable);
  }

}

