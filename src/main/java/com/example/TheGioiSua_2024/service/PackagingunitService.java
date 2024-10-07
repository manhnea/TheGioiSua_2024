package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.repository.PackagingunitRepository;
import com.example.TheGioiSua_2024.service.impl.IPackagingunitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackagingunitService implements IPackagingunitService {
    @Autowired
    private PackagingunitRepository packagingunitRepository;
    @Override
    public Packagingunit addPackagingunit(Packagingunit packagingunit) {
        packagingunit.setStatus(1);
        return packagingunitRepository.save(packagingunit);
    }

    @Override
    public List<Packagingunit> getAllPackagingunit() {
        return packagingunitRepository.findAll();
    }

    @Override
    public Packagingunit updatePackagingunit(Long id, Packagingunit packagingunit) {
        Packagingunit packagingunit1 = packagingunitRepository.findById(id).orElseThrow();
        packagingunit1.setPackagingunitname(packagingunit.getPackagingunitname());
        return packagingunitRepository.save(packagingunit1);
    }

    @Override
    public Packagingunit deletePackagingunit(Long id) {
        Packagingunit packagingunit1 = packagingunitRepository.findById(id).orElseThrow();
        packagingunit1.setStatus(0);
        return packagingunitRepository.save(packagingunit1);
    }
}
