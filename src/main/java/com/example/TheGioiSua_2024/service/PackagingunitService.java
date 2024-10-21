package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.repository.PackagingunitRepository;
import com.example.TheGioiSua_2024.service.impl.IPackagingunitService;
import com.example.TheGioiSua_2024.util.Status;
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
        // Kiểm tra xem tên đã tồn tại chưa
        Optional<Packagingunit> existingContainer = getPackagingunitByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Đơn vị đóng gói với tên này đã tồn tại.";
        }
        packagingunit.setStatus(Status.Active);
        packagingunitRepository.save(packagingunit);
        return "Thêm đơn vị đóng gói thành công.";
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
        // Kiểm tra xem tên đã tồn tại chưa
        Optional<Packagingunit> existingContainer = getPackagingunitByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên đơn vị đóng gói này đã tồn tại.";
        }
        Packagingunit existingPackagingunit = packagingunitRepository.findById(id).orElseThrow();
        existingPackagingunit.setPackagingunitname(packagingunit.getPackagingunitname());
        packagingunitRepository.save(existingPackagingunit);
        return "Cập nhật đơn vị đóng gói thành công.";
    }

    @Override
    public String deletePackagingunit(Long id) {
        Packagingunit existingPackagingunit = packagingunitRepository.findById(id).orElseThrow();
        if (existingPackagingunit.getStatus() == Status.Delete) {
            existingPackagingunit.setStatus(Status.Active);
            return "Khôi phục đơn vị đóng gói thành công.";
        }else {
            existingPackagingunit.setStatus(Status.Delete);
            return "Xóa đơn vị đóng gói thành công.";
        }
    }

    @Override
    public Optional<Packagingunit> getPackagingunitByName(String packagingunitName) {
        return packagingunitRepository.findByPackagingunitname(packagingunitName); // Đã sửa để trả về đối tượng thực tế
    }

    @Override
    public Packagingunit getPackagingunitById(Long id) {
        return packagingunitRepository.findById(id).orElseThrow();
    }
}
