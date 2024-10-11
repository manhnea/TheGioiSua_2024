package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Usermanagement;

import java.util.List;

public interface IUsermanagementService {
    List<Usermanagement> getAllUsermanagement();
    Usermanagement getUsermanagementById(Long id);
    Usermanagement addUsermanagement(Usermanagement usermanagement);
    Usermanagement updateUsermanagement(Long id, Usermanagement usermanagement);
    Usermanagement deleteUsermanagement(Long id);
}
