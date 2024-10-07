package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Packagingunit;

import java.util.List;

public interface IPackagingunitService {
    List<Packagingunit> getAllPackagingunit();
    Packagingunit addPackagingunit(Packagingunit packagingunit);
    Packagingunit updatePackagingunit(Long id,Packagingunit packagingunit);
    Packagingunit deletePackagingunit(Long id);
}
