package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.repository.PackagingunitRepository;
import com.example.TheGioiSua_2024.service.impl.IPackagingunitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackagingunitService implements IPackagingunitService {
    @Autowired
    private PackagingunitRepository packagingunitRepository;
    @Override
    public String addPackagingunit(Packagingunit packagingunit) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = packagingunit.getPackagingunitname().trim();
        packagingunit.setPackagingunitname(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Packagingunit> existingContainer = getPackagingunitByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Packagingunit với tên này đã tồn tại.";
        }
        packagingunit.setStatus(1);
         packagingunitRepository.save(packagingunit);
         return "Them thanh cong";
    }

    @Override
    public List<Packagingunit> getAllPackagingunit() {
        return packagingunitRepository.findAll();
    }

    @Override
    public String updatePackagingunit(Long id, Packagingunit packagingunit) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = packagingunit.getPackagingunitname().trim();
        packagingunit.setPackagingunitname(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Packagingunit> existingContainer = getPackagingunitByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Packagingunit với tên này đã tồn tại.";
        }
        Packagingunit packagingunit1 = packagingunitRepository.findById(id).orElseThrow();
        packagingunit1.setPackagingunitname(packagingunit.getPackagingunitname());
         packagingunitRepository.save(packagingunit1);
         return "Sua thanh cong";
    }

    @Override
    public Packagingunit deletePackagingunit(Long id) {
        Packagingunit packagingunit1 = packagingunitRepository.findById(id).orElseThrow();
        packagingunit1.setStatus(0);
        return packagingunitRepository.save(packagingunit1);
    }

    @Override
    public Optional<Packagingunit> getPackagingunitByName(String packagingunitName) {
        return Optional.empty();
    }
}
