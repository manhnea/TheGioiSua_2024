package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.Milkdetail;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IMilkdetailService {

  List<Milkdetail> getAll();

    String checkDuplicatemilkdetail(Long getProduct, Long getMilkTaste, Long getPackagingunit, Long getUsageCapacity);
  Page<Milkdetail> filterMilkdetails(Long productId, Long milkTasteId, Long packagingUnitId, Long usageCapacityId, Long milkBrandId, Long targetUserId, Long milkTypeId, Pageable pageable);

  ResponseEntity<?> add(String token, Milkdetail milkdetail);

  ResponseEntity<?> update(String token, Long id, Milkdetail milkdetail);

  String delete(String token, Long id);

  Milkdetail getById(Long id);

  MilkDetailDto getMilkDetail(Long packagingunitID, Long milktasteID, Long productID,
      Long usagecapacityID);

  Page<Milkdetail> getMilkDetailPage(Pageable pageable);

  Page<Milkdetail> getMilkDetailsearch(String productname, Pageable pageable);

  long countLowStockMilkDetails();

  long countMilkDetails();

  String updateStockQuantity(String token, Long id, int quantity);

  List<Milkdetail> gethethang();

  Map<String, Object> checkCount(Long id, int quantity);

}
