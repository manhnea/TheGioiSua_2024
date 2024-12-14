package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkbrand;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IMilkbrandService {

  List<Milkbrand> getAllMilkbrands();

    String checkDuplicatemilkbrand(String milkbrand);

  ResponseEntity<?> addMilkbrand(String token, Milkbrand milkbrand);

  ResponseEntity<?> updateMilkbrand(String token, Long id, Milkbrand milkbrand);

  String deleteMilkbrand(String token, Long id);

  Milkbrand getMilkbrandById(Long id);

  Page<Milkbrand> getMilkbrandPage(Pageable pageable);
  Page<Milkbrand> getMilkbrandsearch(String milkbrandname, String status, Pageable pageable);
}
