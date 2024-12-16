package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkbrand;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IMilkbrandService {

  List<Milkbrand> getAllMilkbrands();

  ResponseEntity<?> addMilkbrand(String token, Milkbrand milkbrand);

  ResponseEntity<?> updateMilkbrand(String token, Long id, Milkbrand milkbrand);

  ResponseEntity<?> deleteMilkbrand(String token, Long id);

  Milkbrand getMilkbrandById(Long id);

  Page<Milkbrand> getMilkbrandPage(Pageable pageable);
  Page<Milkbrand> getMilkbrandsearch(String milkbrandname,  Pageable pageable);
}
