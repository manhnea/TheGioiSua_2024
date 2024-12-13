package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsagecapacityService {

  List<Usagecapacity> getAllUsagecapacity();

  String checkDuplicateusagecapacity(int capacity, String unit);

  String addUsagecapacity(String token, Usagecapacity usagecapacity);

  String updateUsagecapacity(String token, Long id, Usagecapacity usagecapacity);

  String deleteUsagecapacity(String token, Long id);

  Usagecapacity getUsagecapacityById(Long id);
  Page<Usagecapacity> getUsagecapacityPage(Pageable pageable);

}
