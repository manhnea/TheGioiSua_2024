package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktasteRepository;
import com.example.TheGioiSua_2024.service.impl.IMilktasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MilktasteService implements IMilktasteService {
    @Autowired
    private MilktasteRepository milktasteRepository;
    @Override
    public List<Milktaste> getAllMilktaste() {
        return milktasteRepository.findAll();
    }

    @Override
    public Milktaste addMilktaste(Milktaste milktaste) {
        milktaste.setStatus(1);
        return milktasteRepository.save(milktaste);
    }

    @Override
    public Milktaste updateMilktaste(Long id, Milktaste milktaste) {
        Milktaste m = milktasteRepository.findById(id).orElseThrow();
        m.setMilktastename(milktaste.getMilktastename());
        return milktasteRepository.save(m);
    }

    @Override
    public Milktaste deleteMilktaste(Long id) {
        Milktaste m = milktasteRepository.findById(id).orElseThrow();
        m.setStatus(0);
        return milktasteRepository.save(m);
    }
}
