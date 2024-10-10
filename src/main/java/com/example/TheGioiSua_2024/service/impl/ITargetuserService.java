package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Size;
import com.example.TheGioiSua_2024.entity.Targetuser;

import java.util.List;
import java.util.Optional;

public interface ITargetuserService {
    List<Targetuser> getAllTargetuser();
    String addTargetuser(Targetuser targetuser);
    String updateTargetuser(Long id,Targetuser targetuser);
    Targetuser deleteTargetuser(Long id);
    Optional<Targetuser> getTargetuserByName(String targetname);
}
