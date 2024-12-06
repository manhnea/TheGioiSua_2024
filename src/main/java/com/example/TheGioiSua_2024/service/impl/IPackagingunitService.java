package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Packagingunit;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPackagingunitService {

  List<Packagingunit> getAllPackagingunit();

  String addPackagingunit(String token, Packagingunit packagingunit);

  String updatePackagingunit(String token, Long id, Packagingunit packagingunit);

  String deletePackagingunit(String token, Long id);

  Optional<Packagingunit> getPackagingunitByName(String packagingunitName);

  Packagingunit getPackagingunitById(Long id);

  Page<Packagingunit> getPackagingunitPage(Pageable pageable);

}
