package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.MilkTypeValidator;
import com.example.TheGioiSua_2024.Validator.TargetuserValidator;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilktypeService;
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
public class MilktypeService implements IMilktypeService {

  @Autowired
  MilktypeRepository milktypeRepository;
  @Autowired
  private logService logService;
  @Autowired
  private JwtUtilities jwtUtilities;


  @Override
  public ResponseEntity<?> AddMilktype(String token, MilkType milktype) {
    String error = MilkTypeValidator.validateMilkType(milktype);
    if (error != null) {
      return ResponseEntity.badRequest().body(Map.of("error", error));
    }
    if (milktypeRepository.existsByMilkTypename(milktype.getMilkTypename())) {
      return ResponseEntity.badRequest().body(Map.of("error", "Loại sữa này đã tồn tại"));
    }
    milktype.setStatus(Status.Active);
    String username = jwtUtilities.extractUsername(token);

    String message = String.format(
      "Tên loại sữa: %s, Mô tả: %s, Trạng thái: %s",
      milktype.getMilkTypename(),
      milktype.getDescription(),
      milktype.getStatus()
    );

    Log log = new Log(); // Tạo log
    log.setAction("Thêm voucher");
    log.setDescription(message);
    logService.saveLog(username, log);
    milktypeRepository.save(milktype);
    return ResponseEntity.ok(Map.of("success", "Thêm loại sữa thành công"));
  }

  @Override
  public ResponseEntity<?> UpdateMilktype(Long id, MilkType milktype) {
    String error = MilkTypeValidator.validateMilkType(milktype);
    if (error != null) {
      return ResponseEntity.badRequest().body(Map.of("error", error));
    }

    MilkType existingMilkType = milktypeRepository.findById(id).orElseThrow();
    String currentMilkTypeName = existingMilkType.getMilkTypename();
    if (currentMilkTypeName.equals(milktype.getMilkTypename())) {
      existingMilkType.setDescription(milktype.getDescription());
      existingMilkType.setStatus(Status.Active);
      milktypeRepository.save(existingMilkType);
     return ResponseEntity.ok(Map.of("success", "Cập nhật loại sữa thành công"));
    }
    if (milktypeRepository.existsByMilkTypename(milktype.getMilkTypename())) {
      return ResponseEntity.badRequest().body(Map.of("error", "Loại sữa này đã tồn tại"));
    }
    MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
    milktype1.setDescription(milktype.getDescription());
    milktype1.setMilkTypename(milktype.getMilkTypename());
    milktypeRepository.save(milktype1);
    return ResponseEntity.ok(Map.of("success", "Cập nhật loại sữa thành công"));
  }

  @Override
  public ResponseEntity<?> DeleteMilktype(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
    if (milktype1.getStatus() == Status.Delete) {
      milktype1.setStatus(Status.Active);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục laoi sua");
      log.setDescription(String.format(milktype1.getMilkTypename()));
      logService.saveLog(username, log);
      milktypeRepository.save(milktype1);
      return ResponseEntity.ok(Map.of("success", "Khôi Phuc Thành Công"));
    } else {
      milktype1.setStatus(Status.Delete);
      Log log = new Log(); // Tạo log
      log.setAction("Xoa laoi sua");
      log.setDescription(String.format(milktype1.getMilkTypename()));
      logService.saveLog(username, log);
      milktypeRepository.save(milktype1);
     return ResponseEntity.ok(Map.of("success", "Xóa Thành Công"));
    }
  }

  @Override
  public Optional<MilkType> getMilkTypeByName(String milktypename) {
    return milktypeRepository.findByMilkTypename(milktypename);
  }

  @Override
  public MilkType GetMilktypeById(Long id) {
    return milktypeRepository.findById(id).orElseThrow();
  }

  @Override
  public List<MilkType> GetAllMilktype() {
    return milktypeRepository.findAll();
  }

  @Override
  public Page<MilkType> GetMilktypePage(Pageable pageable) {
    return milktypeRepository.getMilkTypePage(pageable);
  }

  @Override
  public Page<MilkType> GetMilktypePageByName(String milkTypeName, Pageable pageable) {
    return milktypeRepository.findByMilkTypenameContaining(milkTypeName, pageable);
  }

}
