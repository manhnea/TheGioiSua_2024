package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkbrand;

import java.util.List;

public interface IMilkbrandService {
    List<Milkbrand> getAllMilkbrands();

    String addMilkbrand(String token,Milkbrand milkbrand);

    String updateMilkbrand(Long id, Milkbrand milkbrand);

    String deleteMilkbrand(String token,Long id);

    Milkbrand getMilkbrandById(Long id);
}
