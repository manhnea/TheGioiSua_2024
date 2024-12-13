package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkbrand;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMilkbrandService {

  List<Milkbrand> getAllMilkbrands();

    String checkDuplicatemilkbrand(String milkbrand);

    String addMilkbrand(String token, Milkbrand milkbrand);

  String updateMilkbrand(String token, Long id, Milkbrand milkbrand);

  String deleteMilkbrand(String token, Long id);

  Milkbrand getMilkbrandById(Long id);

  Page<Milkbrand> getMilkbrandPage(Pageable pageable);

}
