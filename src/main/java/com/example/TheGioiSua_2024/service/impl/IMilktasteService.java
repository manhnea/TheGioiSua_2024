package com.example.TheGioiSua_2024.service.impl;


import com.example.TheGioiSua_2024.entity.Milktaste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMilktasteService {
    List<Milktaste> getAllMilktaste();
    String addMilktaste(String token,Milktaste milktaste);
    String updateMilktaste(Long id,Milktaste milktaste);
    String deleteMilktaste(String token,Long id);
    Optional<Milktaste> getMilktasteByName(String milktasteName);

    Milktaste getMilktasteById(Long id);
    Page<Milktaste> getMilktastePage(Pageable pageable);
}
