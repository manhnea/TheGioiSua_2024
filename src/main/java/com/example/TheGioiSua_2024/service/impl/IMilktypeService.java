package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.MilkType;


import java.util.List;
import java.util.Optional;

public interface IMilktypeService {
    List<MilkType> GetAllMilktype();
    String AddMilktype(MilkType milktype);
    String UpdateMilktype(Long id,MilkType milktype);
    MilkType DeleteMilktype(Long id);
    Optional<MilkType> getMilkTypeByName(String milkTypeName);
}
