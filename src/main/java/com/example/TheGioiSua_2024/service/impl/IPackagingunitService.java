package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Packagingunit;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IPackagingunitService {
  List<Packagingunit> getAllPackagingunit();
  ResponseEntity<?> addPackagingunit(String token, Packagingunit packagingunit);

  ResponseEntity<?> updatePackagingunit(String token, Long id, Packagingunit packagingunit);

  ResponseEntity<?> deletePackagingunit(String token, Long id);

  Optional<Packagingunit> getPackagingunitByName(String packagingunitName);

  Packagingunit getPackagingunitById(Long id);

  Page<Packagingunit> getPackagingunitPage(Pageable pageable);

  Page<Packagingunit> getPackagingunitPageByName(String packagingunitName, Pageable pageable);
  
}
