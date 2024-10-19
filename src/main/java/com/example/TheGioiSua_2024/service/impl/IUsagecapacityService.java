package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Usagecapacity;

import java.util.List;

public interface IUsagecapacityService {
    List<Usagecapacity> getAllUsagecapacity();
    String addUsagecapacity(Usagecapacity usagecapacity);
    String updateUsagecapacity(Long id, Usagecapacity usagecapacity);
    String deleteUsagecapacity(Long id);

    Usagecapacity getUsagecapacityById(Long id);
}
