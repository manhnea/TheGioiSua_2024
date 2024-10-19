package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.repository.MilkbrandRepository;
import com.example.TheGioiSua_2024.service.impl.IMilkbrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilkbrandService implements IMilkbrandService {
    @Autowired
    private MilkbrandRepository milkbrandRepository;

    @Override
    public List<Milkbrand> getAllMilkbrands() {
        return milkbrandRepository.findAll();
    }

    @Override
    public String addMilkbrand(Milkbrand milkbrand) {
        String milkbrandName = milkbrand.getMilkbrandname().trim();
        milkbrand.setMilkbrandname(milkbrandName);
        if (milkbrandRepository.findByMilkbrandname(milkbrandName).isPresent()) {
            return "Thương hiệu sữa này đã tồn tại.";
        }
        milkbrand.setStatus(1);
        milkbrandRepository.save(milkbrand);
        return "Thêm thương hiệu sữa thành công.";
    }

    @Override
    public String updateMilkbrand(Long id, Milkbrand milkbrand) {
        Milkbrand existingMilkbrand = milkbrandRepository.findById(id).orElseThrow();
        String currentMilkbrandName = existingMilkbrand.getMilkbrandname();
        if (currentMilkbrandName.equals(milkbrand.getMilkbrandname())) {
            existingMilkbrand.setDescription(milkbrand.getDescription());
            existingMilkbrand.setStatus(1);
            milkbrandRepository.save(existingMilkbrand);
            return "Cập nhật mô tả thương hiệu sữa thành công.";
        } else if (milkbrandRepository.findByMilkbrandname(milkbrand.getMilkbrandname()).isPresent()) {
            return "Thương hiệu sữa này đã tồn tại.";
        }
        existingMilkbrand.setMilkbrandname(milkbrand.getMilkbrandname());
        existingMilkbrand.setStatus(1);
        existingMilkbrand.setDescription(milkbrand.getDescription());
        milkbrandRepository.save(existingMilkbrand);
        return "Cập nhật thương hiệu sữa thành công.";
    }


    @Override
    public String deleteMilkbrand(Long id) {
        Milkbrand existingMilkbrand = milkbrandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thương hiệu sữa không tồn tại"));

        if (existingMilkbrand.getStatus() == 0) {
            existingMilkbrand.setStatus(1);
            milkbrandRepository.save(existingMilkbrand);
            return "Khôi phục thương hiệu sữa thành công.";
        } else {
            existingMilkbrand.setStatus(0);
            milkbrandRepository.save(existingMilkbrand);
            return "Khóa thương hiệu sữa thành công.";
        }
    }

    @Override
    public Milkbrand getMilkbrandById(Long id) {

        return milkbrandRepository.findBydadata(id);
    }


}

