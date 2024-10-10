package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Size;

import java.util.List;
import java.util.Optional;

public interface ISizeService {
    List<Size> findAll();
    String save(Size size);
    Size deleteSizeById(Long id);
    String updateSizeById(Long id, Size size);
    Optional<Size> getSizeByName(String sizeName);
}
