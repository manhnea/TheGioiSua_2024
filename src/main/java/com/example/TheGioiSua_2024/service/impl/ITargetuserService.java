package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Targetuser;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITargetuserService {

  List<Targetuser> getAllTargetuser();

    String checkDuplicatetargetuser(String targetusername);

    String addTargetuser(String token, Targetuser targetuser);

  String updateTargetuser(String token, Long id, Targetuser targetuser);

  String deleteTargetuser(String token, Long id);

  Optional<Targetuser> getTargetuserByName(String targetname);

  Targetuser getTargetuserById(Long id);

  Page<Targetuser> getTargetuserPage(Pageable pageable);

}
