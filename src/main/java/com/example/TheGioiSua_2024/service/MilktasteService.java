package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktasteRepository;
import com.example.TheGioiSua_2024.service.impl.IMilktasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilktasteService implements IMilktasteService {
    @Autowired
    private MilktasteRepository milktasteRepository;
    @Override
    public List<Milktaste> getAllMilktaste() {
        return milktasteRepository.findAll();
    }

    @Override
    public String addMilktaste(Milktaste milktaste) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = milktaste.getMilktastename().trim();
        milktaste.setMilktastename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Milktaste> existingContainer = getMilktasteByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Container với tên này đã tồn tại.";
        }
        milktaste.setStatus(1);
        return "Thêm thành công";
    }

    @Override
    public String updateMilktaste(Long id, Milktaste milktaste) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = milktaste.getMilktastename().trim();
        milktaste.setMilktastename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Milktaste> existingContainer = getMilktasteByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Container với tên này đã tồn tại.";
        }
        Milktaste m = milktasteRepository.findById(id).orElseThrow();
        m.setMilktastename(milktaste.getMilktastename());
        milktasteRepository.save(m);
        return "Thêm thành công";
    }

    @Override
    public Milktaste deleteMilktaste(Long id) {
        Milktaste m = milktasteRepository.findById(id).orElseThrow();
        m.setStatus(0);
        return milktasteRepository.save(m);
    }

    @Override
    public Optional<Milktaste> getMilktasteByName(String milktasteName) {
        return milktasteRepository.findByMilktastename(milktasteName);
    }
}
