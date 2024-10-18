package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.repository.UsagecapacityRepository;
import com.example.TheGioiSua_2024.service.impl.IUsagecapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsagecapacityService implements IUsagecapacityService {
    @Autowired
    private UsagecapacityRepository usagecapacityRepository;

    @Override
    public String addUsagecapacity(Usagecapacity usagecapacity) {
        usagecapacity.setStatus(1);
        usagecapacityRepository.save(usagecapacity);
        return "Đã thêm đơn vị sử dụng thành công.";
    }

    @Override
    public List<Usagecapacity> getAllUsagecapacity() {
        return usagecapacityRepository.findAll();
    }

    @Override
    public String updateUsagecapacity(Long id, Usagecapacity usagecapacity) {
        Usagecapacity existingUsagecapacity = usagecapacityRepository.findById(id).orElseThrow();
        String currentUnit = existingUsagecapacity.getUnit();

        if (currentUnit.equals(usagecapacity.getUnit())) {
            // Nếu đơn vị không thay đổi, chỉ cập nhật dung lượng
            existingUsagecapacity.setCapacity(usagecapacity.getCapacity());
            existingUsagecapacity.setStatus(1);
            usagecapacityRepository.save(existingUsagecapacity);
            return "Đã cập nhật đơn vị sử dụng thành công.";
        }
        // Kiểm tra xem đơn vị mới có bị trùng không
        else if (usagecapacityRepository.findByUnit(usagecapacity.getUnit()).isPresent()) {
            return "Đơn vị này đã tồn tại.";
        }

        // Cập nhật đơn vị và dung lượng mới
        existingUsagecapacity.setCapacity(usagecapacity.getCapacity());
        existingUsagecapacity.setUnit(usagecapacity.getUnit());
        existingUsagecapacity.setStatus(1);
        usagecapacityRepository.save(existingUsagecapacity);
        return "Đã cập nhật đơn vị sử dụng thành công.";
    }

    @Override
    public Usagecapacity deleteUsagecapacity(Long id) {
        Usagecapacity existingUsagecapacity = usagecapacityRepository.findById(id).orElseThrow();
        existingUsagecapacity.setStatus(0);  // Đặt trạng thái ngừng hoạt động
        return usagecapacityRepository.save(existingUsagecapacity);
    }

    @Override
    public Usagecapacity getUsagecapacityById(Long id) {
        return usagecapacityRepository.findById(id).orElseThrow();
    }
}
