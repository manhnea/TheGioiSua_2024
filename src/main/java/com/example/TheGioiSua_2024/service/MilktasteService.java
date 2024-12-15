package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.MilkTasteValidator;
import com.example.TheGioiSua_2024.Validator.UsagecapacityValidator;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktasteRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilktasteService;
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
public class MilktasteService implements IMilktasteService {

  @Autowired
  private MilktasteRepository milktasteRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;

  @Override
  public List<Milktaste> getAllMilktaste() {
    return milktasteRepository.findAll();
  }
  @Override
  public String checkDuplicatemilktaste(String milktaste) {
    // Check if Usagecapacity with the same capacity and unit already exists
    Optional<Milktaste> existingUsagecapacity = milktasteRepository.findByMilktastename(milktaste);

    if (existingUsagecapacity.isPresent()) {
      return "Kết hợp capacity và unit này đã tồn tại."; // Capacity and unit combination already exists
    }
    return null; // No duplicates found
  }

  @Override
  public ResponseEntity<?> addMilktaste(String token, Milktaste milktaste) {
    String error = MilkTasteValidator.validateMilktaste(milktaste);
    if (error != null) {
      return ResponseEntity.badRequest().body(Map.of("error", error));
    }
    if (milktasteRepository.existsByMilktastename(milktaste.getMilktastename())) {
      return ResponseEntity.badRequest().body(Map.of("error", "Vị Sữa Đã Tồn Tại"));
    }
    milktaste.setStatus(Status.Active);
    String username = jwtUtilities.extractUsername(token);

    String message = String.format(
      "Trạng thái: %s, Tên hương vị: %s",
      milktaste.getStatus(),
      milktaste.getMilktastename()
    );

    Log log = new Log(); // Tạo log
    log.setAction("Thêm voucher");
    log.setDescription(message);
    logService.saveLog(username, log);
    milktasteRepository.save(milktaste);
    return  ResponseEntity.ok(Map.of("succecc","Thành Công"));
  }

  @Override
  public ResponseEntity<?> updateMilktaste(Long id, Milktaste milktaste) {
    String error = MilkTasteValidator.validateMilktaste(milktaste);
    if (error != null) {
      return ResponseEntity.badRequest().body(Map.of("error", error));
    }
    if (milktasteRepository.existsByMilktastename(milktaste.getMilktastename())) {
      return ResponseEntity.badRequest().body(Map.of("error", "Vị Sữa Đã Tồn Tại"));
    }
    Milktaste m = milktasteRepository.findById(id).orElseThrow();
    m.setMilktastename(milktaste.getMilktastename());
    milktasteRepository.save(m);
    return  ResponseEntity.ok(Map.of("succecc","Thành Công"));
  }

  @Override
  public ResponseEntity<?> deleteMilktaste(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Milktaste existingMilktaste = milktasteRepository.findById(id).orElseThrow();
    if (existingMilktaste.getStatus() == Status.Delete) {
      existingMilktaste.setStatus(Status.Active);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục vị");
      log.setDescription(String.format(existingMilktaste.getMilktastename()));
      logService.saveLog(username, log);
      milktasteRepository.save(existingMilktaste);
      return  ResponseEntity.ok(Map.of("succecc","Khôi phục Thành Công"));
    } else {
      existingMilktaste.setStatus(Status.Delete);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục vị");
      log.setDescription(String.format(existingMilktaste.getMilktastename()));
      logService.saveLog(username, log);
      milktasteRepository.save(existingMilktaste);
      return  ResponseEntity.ok(Map.of("succecc"," Xóa Thành Công"));
    }
  }

  @Override
  public Optional<Milktaste> getMilktasteByName(String milktasteName) {
    return milktasteRepository.findByMilktastename(milktasteName);
  }

  @Override
  public Milktaste getMilktasteById(Long id) {
    return milktasteRepository.findById(id).orElseThrow();
  }

  @Override
  public Page<Milktaste> getMilktastePage(Pageable pageable) {
    return milktasteRepository.getMilktastePage(pageable);
  }

  @Override
  public Page<Milktaste> getMilktastePageByName(String milktasteName, Pageable pageable) {
    return milktasteRepository.findByMilktastenamePage(milktasteName, pageable);
  }
}
