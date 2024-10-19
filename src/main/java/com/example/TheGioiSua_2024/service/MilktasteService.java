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
        // Kiểm tra xem tên đã tồn tại chưa
        Optional<Milktaste> existingContainer = getMilktasteByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên vị sữa này đã tồn tại.";
        }
        milktaste.setStatus(1);
        milktasteRepository.save(milktaste); // Lưu đối tượng milktaste vào cơ sở dữ liệu
        return "Thêm vị sữa thành công";
    }

    @Override
    public String updateMilktaste(Long id, Milktaste milktaste) {
        Milktaste m = milktasteRepository.findById(id).orElseThrow();
        String trimmedName = milktaste.getMilktastename().trim();
        milktaste.setMilktastename(trimmedName);
        Optional<Milktaste> existingContainer = getMilktasteByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên vị sữa này đã tồn tại.";
        }
        m.setMilktastename(milktaste.getMilktastename());
        milktasteRepository.save(m);
        return "Cập nhật vị sữa thành công"; // Đã thay đổi thông báo
    }

    @Override
    public String deleteMilktaste(Long id) {
        Milktaste existingMilktaste = milktasteRepository.findById(id).orElseThrow();
        if (existingMilktaste.getStatus() == 0) {
            existingMilktaste.setStatus(1);
            milktasteRepository.save(existingMilktaste);
            return "Khôi phục vị sữa thành công!";
        } else {
            existingMilktaste.setStatus(0);
            milktasteRepository.save(existingMilktaste);
            return "Xóa vị sữa thành công!";
        }
    }

    @Override
    public Optional<Milktaste> getMilktasteByName(String milktasteName) {
        return milktasteRepository.findByMilktastename(milktasteName);
    }

    @Override
    public Milktaste getMilktasteById(Long id) {
        return milktasteRepository.findById(id).orElseThrow();
    }
}
