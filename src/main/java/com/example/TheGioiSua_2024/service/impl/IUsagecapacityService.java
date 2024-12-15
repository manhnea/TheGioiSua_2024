package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface IUsagecapacityService {
  List<Usagecapacity> getAllUsagecapacity();

  ResponseEntity<?>  addUsagecapacity(String token, Usagecapacity usagecapacity);

  ResponseEntity<?>  updateUsagecapacity(String token, Long id, Usagecapacity usagecapacity);

  ResponseEntity<?>  deleteUsagecapacity(String token, Long id);

  Usagecapacity getUsagecapacityById(Long id);
  Page<Usagecapacity> getUsagecapacityPage(Pageable pageable);

//  Page<Usagecapacity> getUsagecapacityPageByCapacity(int capacity,String unit, Pageable pageable);
}
