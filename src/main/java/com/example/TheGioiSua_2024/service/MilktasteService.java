package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktasteRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilktasteService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
  public String addMilktaste(String token, Milktaste milktaste) {
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
    return "Thêm vị sữa thành công";
  }

  @Override
  public String updateMilktaste(Long id, Milktaste milktaste) {
    Milktaste m = milktasteRepository.findById(id).orElseThrow();
    m.setMilktastename(milktaste.getMilktastename());
    milktasteRepository.save(m);
    return "Cập nhật vị sữa thành công";
  }

  @Override
  public String deleteMilktaste(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Milktaste existingMilktaste = milktasteRepository.findById(id).orElseThrow();
    if (existingMilktaste.getStatus() == Status.Delete) {
      existingMilktaste.setStatus(Status.Active);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục vị");
      log.setDescription(String.format(existingMilktaste.getMilktastename()));
      logService.saveLog(username, log);
      milktasteRepository.save(existingMilktaste);
      return "Khôi phục vị sữa thành công!";
    } else {
      existingMilktaste.setStatus(Status.Delete);
      Log log = new Log(); // Tạo log
      log.setAction("Khôi phục vị");
      log.setDescription(String.format(existingMilktaste.getMilktastename()));
      logService.saveLog(username, log);
      milktasteRepository.save(existingMilktaste);
      return "Xóa vị sữa thành công!";
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
    return milktasteRepository.findAll(pageable);
  }
}
