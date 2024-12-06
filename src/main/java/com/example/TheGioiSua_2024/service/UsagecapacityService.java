package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.repository.UsagecapacityRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IUsagecapacityService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsagecapacityService implements IUsagecapacityService {

  @Autowired
  private UsagecapacityRepository usagecapacityRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;

  @Override
  public String addUsagecapacity(String token, Usagecapacity usagecapacity) {
    String username = jwtUtilities.extractUsername(token);

    usagecapacity.setStatus(1);
    Log log = new Log(); // Tạo log
    log.setAction("Thêm đơn vị sử dụng");
    log.setDescription(
        String.format("Thêm đơn vị sử dụng %s với dung lượng %d", usagecapacity.getUnit(),
            usagecapacity.getCapacity()));
    logService.saveLog(username, log);
    usagecapacityRepository.save(usagecapacity);
    return "Đã thêm đơn vị sử dụng thành công.";
  }

  @Override
  public List<Usagecapacity> getAllUsagecapacity() {
    return usagecapacityRepository.findAll();
  }

  @Override
  public String updateUsagecapacity(String token, Long id, Usagecapacity usagecapacity) {
    Usagecapacity existingUsagecapacity = usagecapacityRepository.findById(id).orElseThrow();
    String currentUnit = existingUsagecapacity.getUnit();
    String username = jwtUtilities.extractUsername(token);
    Log log = new Log(); // Tạo log

    if (currentUnit.equals(usagecapacity.getUnit())) {
      existingUsagecapacity.setCapacity(usagecapacity.getCapacity());
      existingUsagecapacity.setStatus(Status.Active);
      log.setAction("Cập nhật đơn vị sử dụng");
      log.setDescription(String.format("Cập nhat giá trị dung lượng của đơn vị sử dụng %s thành %d",
          usagecapacity.getUnit(), usagecapacity.getCapacity()));
      logService.saveLog(username, log);
      usagecapacityRepository.save(existingUsagecapacity);
      return "Đã cập nhật đơn vị sử dụng thành công.";
    } else if (usagecapacityRepository.findByUnit(usagecapacity.getUnit()).isPresent()) {
      return "Đơn vị này đã tồn tại.";
    }
    existingUsagecapacity.setCapacity(usagecapacity.getCapacity());
    existingUsagecapacity.setUnit(usagecapacity.getUnit());
    existingUsagecapacity.setStatus(Status.Active);
    log.setAction("Cập nhật đơn vị sử dụng");
    log.setDescription(String.format("Cập nhật đơn vị sử dụng %s thành %s với dung lượng %d",
        currentUnit, usagecapacity.getUnit(), usagecapacity.getCapacity()));
    usagecapacityRepository.save(existingUsagecapacity);
    return "Đã cập nhật đơn vị sử dụng thành công.";
  }

  @Override
  public String deleteUsagecapacity(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Log log = new Log(); // Tạo log
    Usagecapacity existingUsagecapacity = usagecapacityRepository.findById(id).orElseThrow();
    if (existingUsagecapacity.getStatus() == Status.Delete) {
      existingUsagecapacity.setStatus(Status.Active);
      log.setAction("Khôi phục đơn vị sử dụng");
      log.setDescription(
          String.format("Khôi phục đơn vị sử dụng %s", existingUsagecapacity.getUnit()));
      usagecapacityRepository.save(existingUsagecapacity);
      return "Khôi phục đơn vị sử dụng thành công.";
    } else {
      existingUsagecapacity.setStatus(Status.Delete);
      log.setAction("Xóa đơn vị sử dụng");
      log.setDescription(String.format("Xóa đơn vị sử dụng %s", existingUsagecapacity.getUnit()));
      usagecapacityRepository.save(existingUsagecapacity);
      return "Đã xóa đơn vị sử dụng thành công.";
    }
  }

  @Override
  public Usagecapacity getUsagecapacityById(Long id) {
    return usagecapacityRepository.findById(id).orElseThrow();
  }

  @Override
  public Page<Usagecapacity> getUsagecapacityPage(Pageable pageable) {
    return usagecapacityRepository.findAll(pageable);
  }
}
