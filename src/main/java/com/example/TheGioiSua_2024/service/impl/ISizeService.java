package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Size;

import java.util.List;

public interface ISizeService {
    List<Size> findAll();
    Size save(Size size);
    Size deleteSizeById(Long id);
    Size updateSizeById(Long id, Size size);
}
