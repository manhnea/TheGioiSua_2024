package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.MilkType;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IMilktypeService {

  List<MilkType> GetAllMilktype();

  ResponseEntity<?> AddMilktype(String token, MilkType milktype);

  ResponseEntity<?> UpdateMilktype(Long id, MilkType milktype);

  ResponseEntity<?> DeleteMilktype(String token, Long id);

  Optional<MilkType> getMilkTypeByName(String milkTypeName);

  MilkType GetMilktypeById(Long id);

  Page<MilkType> GetMilktypePage(Pageable pageable);

  Page<MilkType> GetMilktypePageByName(String milkTypeName, Pageable pageable);
}
