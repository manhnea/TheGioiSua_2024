package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
import com.example.TheGioiSua_2024.service.impl.ITargetuserService;
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
        String trimmedName = targetuser.getTargetusername().trim();
        targetuser.setTargetusername(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Targetuser> existingContainer = getTargetuserByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Targetuser với tên này đã tồn tại.";
        }
        targetuser.setStatus(1);
         targetuserRepository.save(targetuser);
         return "Added Successfully";
    }

    @Override
    public String updateTargetuser(Long id, Targetuser targetuser) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = targetuser.getTargetusername().trim();
        targetuser.setTargetusername(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Targetuser> existingContainer = getTargetuserByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Targetuser với tên này đã tồn tại.";
        }
        Targetuser targetuser1 = targetuserRepository.findById(id).orElseThrow();
        targetuser1.setDescription(targetuser.getDescription());
        targetuser1.setTargetusername(targetuser.getTargetusername());
         targetuserRepository.save(targetuser1);
         return "Updated Successfully";
    }

    @Override
    public Targetuser deleteTargetuser(Long id) {
        Targetuser targetuser1 = targetuserRepository.findById(id).orElseThrow();
        targetuser1.setStatus(0);
        return targetuserRepository.save(targetuser1);
    }

    @Override
    public Optional<Targetuser> getTargetuserByName(String targetname) {
        return targetuserRepository.findByTargetusername(targetname);
    }


}
