package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Size;
import com.example.TheGioiSua_2024.repository.SizeRepository;
import com.example.TheGioiSua_2024.service.impl.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService implements ISizeService {
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
    public Size save(Size size) {
        size.setStatus(1);
        return sizeRepository.save(size);
    }
    @Override
    public Size updateSizeById(Long id, Size size) {
        Size sizeedit = sizeRepository.findById(id).orElseThrow();
        sizeedit.setSizename(size.getSizename());
        return sizeRepository.save(sizeedit);
    }
}
