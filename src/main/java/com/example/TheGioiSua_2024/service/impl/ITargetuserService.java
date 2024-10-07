package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Targetuser;

import java.util.List;

public interface ITargetuserService {
    List<Targetuser> getAllTargetuser();
    Targetuser addTargetuser(Targetuser targetuser);
    Targetuser updateTargetuser(Long id,Targetuser targetuser);
    Targetuser deleteTargetuser(Long id);
}
