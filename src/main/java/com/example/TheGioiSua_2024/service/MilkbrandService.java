package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.repository.MilkbrandRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilkbrandService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public String addMilkbrand(String token, Milkbrand milkbrand) {
    String username = jwtUtilities.extractUsername(token);
    String message = String.format(
      "Tên thương hiệu: %s, Mô tả: %s, trạng thái: %s",
      milkbrand.getMilkbrandname(),
      milkbrand.getDescription(),
      milkbrand.getStatus()
    );

    Log log = new Log(); // Tạo log
    log.setAction("Thêm voucher");
    log.setDescription(message);
    logService.saveLog(username, log);
    milkbrand.setStatus(Status.Active);
    milkbrandRepository.save(milkbrand);
    return "Thêm thương hiệu sữa thành công.";
  }

  @Override
  public String updateMilkbrand(String token, Long id, Milkbrand milkbrand) {
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
      return "Cập nhật mô tả thương hiệu sữa thành công.";
    } else if (milkbrandRepository.findByMilkbrandname(milkbrand.getMilkbrandname()).isPresent()) {
      return "Thương hiệu sữa này đã tồn tại.";
    }
    existingMilkbrand.setMilkbrandname(milkbrand.getMilkbrandname());
    existingMilkbrand.setStatus(Status.Active);
    existingMilkbrand.setDescription(milkbrand.getDescription());
    milkbrandRepository.save(existingMilkbrand);
    log.setAction("Cập nhật mô tả brand");
    log.setDescription(message);
    logService.saveLog(username, log);
    return "Cập nhật thương hiệu sữa thành công.";
  }


  @Override
  public String deleteMilkbrand(String token, Long id) {
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
      return "Khôi phục thương hiệu sữa thành công.";
    } else {
      existingMilkbrand.setStatus(Status.Delete);
      Log log = new Log(); // Tạo log
      log.setAction("Khóa brand");
      log.setDescription(String.format(existingMilkbrand.getMilkbrandname()));
      logService.saveLog(username, log);
      milkbrandRepository.save(existingMilkbrand);
      return "Khóa thương hiệu sữa thành công.";
    }
  }

  @Override
  public Milkbrand getMilkbrandById(Long id) {

    return milkbrandRepository.findBydadata(id);
  }

  @Override
  public Page<Milkbrand> getMilkbrandPage(Pageable pageable) {
    return milkbrandRepository.findAll(pageable);
  }

}

