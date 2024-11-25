package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Usagecapacity;

import java.util.List;

public interface IUsagecapacityService {

  List<Usagecapacity> getAllUsagecapacity();

  String addUsagecapacity(String token, Usagecapacity usagecapacity);

  String updateUsagecapacity(String token, Long id, Usagecapacity usagecapacity);

  String deleteUsagecapacity(String token, Long id);

  Usagecapacity getUsagecapacityById(Long id);
}
