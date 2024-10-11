package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkbrand;

import java.util.List;

public interface IMilkbrandService {
    List<Milkbrand> getAllMilkbrands();

    String addMilkbrand(Milkbrand milkbrand);

    String updateMilkbrand(Long id, Milkbrand milkbrand);

    void deleteMilkbrand(Long id, Milkbrand milkbrand);
}
