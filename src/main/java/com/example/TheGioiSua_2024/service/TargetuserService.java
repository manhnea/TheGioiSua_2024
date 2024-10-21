package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
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

    @Override
    public List<Targetuser> getAllTargetuser() {
        return targetuserRepository.findAll();
    }

    @Override
    public String addTargetuser(Targetuser targetuser) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = targetuser.getTargetName().trim();
        targetuser.setTargetName(trimmedName);
        // Kiểm tra xem tên đã tồn tại chưa
        Optional<Targetuser> existingContainer = getTargetuserByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên người dùng mục tiêu này đã tồn tại.";
        }
        targetuser.setStatus(Status.Active);
        targetuserRepository.save(targetuser);
        return "Thêm người dùng mục tiêu thành công.";
    }

    @Override
    public String updateTargetuser(Long id, Targetuser targetuser) {
        Targetuser existingTargetuser = targetuserRepository.findById(id).orElseThrow();
        String currentTargetName = existingTargetuser.getTargetName();
        // Nếu tên không thay đổi, chỉ cập nhật mô tả
        if (currentTargetName.equals(targetuser.getTargetName())) {
            existingTargetuser.setDescription(targetuser.getDescription());
            existingTargetuser.setStatus(Status.Active);
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
        targetuserRepository.save(existingTargetuser);
        return "Cập nhật người dùng mục tiêu thành công.";
    }

    @Override
    public String deleteTargetuser(Long id) {
        Targetuser existingTargetuser = targetuserRepository.findById(id).orElseThrow();
       if (existingTargetuser.getStatus() == Status.Delete) {
            existingTargetuser.setStatus(Status.Active);
            targetuserRepository.save(existingTargetuser);
            return "Khôi phục người dùng mục tiêu thành công.";
        } else {
            existingTargetuser.setStatus(Status.Delete);
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
