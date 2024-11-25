package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.ITargetuserService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TargetuserService implements ITargetuserService {

  @Autowired
  private TargetuserRepository targetuserRepository;
  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private logService logService;

  @Override
  public List<Targetuser> getAllTargetuser() {
    return targetuserRepository.findAll();
  }

  @Override
  public String addTargetuser(String token, Targetuser targetuser) {
    String username = jwtUtilities.extractUsername(token);
    // Loại bỏ khoảng trắng ở đầu và cuối tên
    String trimmedName = targetuser.getTargetName().trim();
    targetuser.setTargetName(trimmedName);
    // Kiểm tra xem tên đã tồn tại chưa
    Optional<Targetuser> existingContainer = getTargetuserByName(trimmedName);
    if (existingContainer.isPresent()) {
      return "Tên người dùng mục tiêu này đã tồn tại.";
    }
    targetuser.setStatus(Status.Active);
    Log log = new Log(); // Tạo log
    log.setAction("Thêm người dùng mục tiêu");
    log.setDescription("Thêm người dùng mục tiêu " + targetuser.getTargetName());
    logService.saveLog(username, log);
    targetuserRepository.save(targetuser);
    return "Thêm người dùng mục tiêu thành công.";
  }

  @Override
  public String updateTargetuser(String token, Long id, Targetuser targetuser) {
    String username = jwtUtilities.extractUsername(token);
    Log log = new Log(); // Tạo log
    Targetuser existingTargetuser = targetuserRepository.findById(id).orElseThrow();
    String oldTargetName = existingTargetuser.getTargetName();
    String newTargetName = targetuser.getTargetName();
    String oldDescription = existingTargetuser.getDescription();
    String newDescription = targetuser.getDescription();

    String currentTargetName = existingTargetuser.getTargetName();
    // Nếu tên không thay đổi, chỉ cập nhật mô tả
    if (currentTargetName.equals(targetuser.getTargetName())) {
      existingTargetuser.setDescription(targetuser.getDescription());
      existingTargetuser.setStatus(Status.Active);
      log.setAction("Cập nhật người dùng mục tiêu");
      log.setDescription("Cập nhật người dùng mục tiêu " + oldTargetName + " thành " + newTargetName
          + " và mô tả từ " + oldDescription + " thành " + newDescription);
      logService.saveLog(username, log);
      targetuserRepository.save(existingTargetuser);
      return "Cập nhật người dùng mục tiêu thành công.";
    }
    // Kiểm tra xem tên mới có bị trùng không
    else if (targetuserRepository.findByTargetusername(targetuser.getTargetName()).isPresent()) {
      return "Tên người dùng mục tiêu này đã tồn tại.";
    }
    // Cập nhật tên và mô tả mới
    existingTargetuser.setDescription(targetuser.getDescription());
    existingTargetuser.setTargetName(targetuser.getTargetName());
    log.setAction("Cập nhật người dùng mục tiêu");
    log.setDescription("Cập nhật người dùng mục tiêu " + oldTargetName + " thành " + newTargetName
        + " và mô tả từ " + oldDescription + " thành " + newDescription);
    targetuserRepository.save(existingTargetuser);
    return "Cập nhật người dùng mục tiêu thành công.";
  }

  @Override
  public String deleteTargetuser(String token, Long id) {
    String username = jwtUtilities.extractUsername(token);
    Log log = new Log(); // Tạo log
    Targetuser existingTargetuser = targetuserRepository.findById(id).orElseThrow();
    if (existingTargetuser.getStatus() == Status.Delete) {
      existingTargetuser.setStatus(Status.Active);
      log.setAction("Khôi phục người dùng mục tiêu");
      log.setDescription("Khôi phục người dùng mục tiêu " + existingTargetuser.getTargetName());
      logService.saveLog(username, log);
      targetuserRepository.save(existingTargetuser);
      return "Khôi phục người dùng mục tiêu thành công.";
    } else {
      existingTargetuser.setStatus(Status.Delete);
      log.setAction("Xóa người dùng mục tiêu");
      log.setDescription("Xóa người dùng mục tiêu " + existingTargetuser.getTargetName());
      targetuserRepository.save(existingTargetuser);
      return "Xóa người dùng mục tiêu thành công.";
    }
  }

  @Override
  public Optional<Targetuser> getTargetuserByName(String targetname) {
    return targetuserRepository.findByTargetusername(targetname);
  }

  @Override
  public Targetuser getTargetuserById(Long id) {
    return targetuserRepository.findById(id).orElseThrow();
  }
}
