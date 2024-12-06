package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.MilkType;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMilktypeService {

  List<MilkType> GetAllMilktype();

  String AddMilktype(String token, MilkType milktype);

  String UpdateMilktype(Long id, MilkType milktype);

  String DeleteMilktype(String token, Long id);

  Optional<MilkType> getMilkTypeByName(String milkTypeName);

  MilkType GetMilktypeById(Long id);

  Page<MilkType> GetMilktypePage(Pageable pageable);

}
