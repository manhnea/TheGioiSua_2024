package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milktaste;

import java.util.List;

public interface IMilktasteService {
    List<Milktaste> getAllMilktaste();
    Milktaste addMilktaste(Milktaste milktaste);
    Milktaste updateMilktaste(Long id,Milktaste milktaste);
    Milktaste deleteMilktaste(Long id);
}
