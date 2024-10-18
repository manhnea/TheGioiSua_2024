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
    UsagecapacityRepository usagecapacityRepository;
    @Override
    public String addUsagecapacity(Usagecapacity usagecapacity) {
        usagecapacity.setStatus(1);
        return "Đã thêm thành công.";
    }

    @Override
    public List<Usagecapacity> getAllUsagecapacity() {
        return usagecapacityRepository.findAll();
    }

    @Override
    public String updateUsagecapacity(Long id, Usagecapacity usagecapacity) {
        Usagecapacity  usagecapacity1 = usagecapacityRepository.findById(id).orElseThrow();
        String currentunit = usagecapacity1.getUnit();
        if (currentunit.equals(usagecapacity.getUnit())) {
            usagecapacity1.setCapacity(usagecapacity.getCapacity());
            usagecapacity1.setStatus(1);
            return "Đã cập nhật thành công.";
        } else if (usagecapacityRepository.findByUnit(usagecapacity.getUnit()).isPresent()) {
            return "Đơn vị này đã tồn tại.";
        }
        usagecapacity1.setCapacity(usagecapacity.getCapacity());
        usagecapacity1.setUnit(usagecapacity.getUnit());
        usagecapacity1.setStatus(1);
        return "Đã cập nhật thành công.";
    }

    @Override
    public Usagecapacity deleteUsagecapacity(Long id) {
        Usagecapacity  usagecapacity1 = usagecapacityRepository.findById(id).orElseThrow();
        usagecapacity1.setStatus(0);
        return usagecapacityRepository.save(usagecapacity1);
    }

    @Override
    public Usagecapacity getUsagecapacityById(Long id) {
        return usagecapacityRepository.findById(id).orElseThrow();
    }
}
