package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Targetuser;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ITargetuserService {

  List<Targetuser> getAllTargetuser();

  ResponseEntity<?> addTargetuser(String token, Targetuser targetuser);

  ResponseEntity<?> updateTargetuser(String token, Long id, Targetuser targetuser);

  ResponseEntity<?> deleteTargetuser(String token, Long id);

  Optional<Targetuser> getTargetuserByName(String targetname);

  Targetuser getTargetuserById(Long id);

  Page<Targetuser> getTargetuserPage(Pageable pageable);

  Page<Targetuser> getTargetusersearch(String targetname, Pageable pageable);

}
