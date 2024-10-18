package com.example.TheGioiSua_2024.service.impl;


import com.example.TheGioiSua_2024.entity.Milktaste;

import java.util.List;
import java.util.Optional;

public interface IMilktasteService {
    List<Milktaste> getAllMilktaste();
    String addMilktaste(Milktaste milktaste);
    String updateMilktaste(Long id,Milktaste milktaste);
    String deleteMilktaste(Long id);
    Optional<Milktaste> getMilktasteByName(String milktasteName);

    Milktaste getMilktasteById(Long id);
}
