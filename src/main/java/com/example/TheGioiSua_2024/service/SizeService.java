package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.entity.Size;
import com.example.TheGioiSua_2024.repository.SizeRepository;
import com.example.TheGioiSua_2024.service.impl.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService implements ISizeService {
    @Override
    public Optional<Size> getSizeByName(String sizeName) {
        return sizeRepository.findBySizename(sizeName);
    }

    @Autowired
    private SizeRepository sizeRepository;
    @Override
    public Size deleteSizeById(Long id ) {
        Size sizeedit = sizeRepository.findById(id).orElseThrow();
        sizeedit.setStatus(0);
        return sizeRepository.save(sizeedit);
    }

    @Override
    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    public String save(Size size) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = size.getSizename().trim();
        size.setSizename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Size> existingContainer = getSizeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Packagingunit với tên này đã tồn tại.";
        }

        size.setStatus(1);
        sizeRepository.save(size);
        return "Them thanh cong";
    }
    @Override
    public String updateSizeById(Long id, Size size) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = size.getSizename().trim();
        size.setSizename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Size> existingContainer = getSizeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Packagingunit với tên này đã tồn tại.";
        }
        Size sizeedit = sizeRepository.findById(id).orElseThrow();
        sizeedit.setSizename(size.getSizename());
        sizeRepository.save(sizeedit);
        return "Sua thanh cong";
    }
}
