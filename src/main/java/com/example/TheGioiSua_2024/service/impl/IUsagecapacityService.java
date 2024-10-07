package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Usagecapacity;

import java.util.List;

public interface IUsagecapacityService {
    List<Usagecapacity> getAllUsagecapacity();
    Usagecapacity addUsagecapacity(Usagecapacity usagecapacity);
    Usagecapacity updateUsagecapacity(Long id, Usagecapacity usagecapacity);
    Usagecapacity deleteUsagecapacity(Long id);
}
