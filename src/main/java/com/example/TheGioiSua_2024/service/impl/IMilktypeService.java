package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.MilkType;

import java.util.List;

public interface IMilktypeService {
    List<MilkType> GetAllMilktype();
    MilkType AddMilktype(MilkType milktype);
    MilkType UpdateMilktype(Long id,MilkType milktype);
    MilkType DeleteMilktype(Long id);
}
