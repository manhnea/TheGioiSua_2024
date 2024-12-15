package com.example.TheGioiSua_2024.service.impl;


import com.example.TheGioiSua_2024.entity.Milktaste;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IMilktasteService {

  List<Milktaste> getAllMilktaste();

    String checkDuplicatemilktaste(String milktaste);

 ResponseEntity<?> addMilktaste(String token, Milktaste milktaste);

  ResponseEntity<?>  updateMilktaste(Long id, Milktaste milktaste);

  ResponseEntity<?>  deleteMilktaste(String token, Long id);

  Optional<Milktaste> getMilktasteByName(String milktasteName);

  Milktaste getMilktasteById(Long id);

  Page<Milktaste> getMilktastePage(Pageable pageable);
  Page<Milktaste> getMilktastePageByName(String milktasteName, Pageable pageable);
}
