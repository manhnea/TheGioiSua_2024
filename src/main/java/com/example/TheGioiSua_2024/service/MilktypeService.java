package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.service.impl.IMilktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilktypeService implements IMilktypeService {
    @Autowired
    MilktypeRepository milktypeRepository;

    @Override
    public String AddMilktype(MilkType milktype) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = milktype.getMilktypename().trim();
        milktype.setMilktypename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<MilkType> existingContainer = getMilkTypeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "MilkType với tên này đã tồn tại.";
        }
        milktype.setStatus(1);
        milktypeRepository.save(milktype);
        return "Them Thanh Cong";
    }

    @Override
    public String UpdateMilktype(Long id, MilkType milktype) {
        // Loại bỏ khoảng trắng ở đầu và cuối tên
        String trimmedName = milktype.getMilktypename().trim();
        milktype.setMilktypename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<MilkType> existingContainer = getMilkTypeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "MilkType với tên này đã tồn tại.";
        }
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setDescription(milktype.getDescription());
        milktype1.setMilktypename(milktype.getMilktypename());
         milktypeRepository.save(milktype1);
         return "Sua Thanh Cong";
    }

    @Override
    public MilkType DeleteMilktype(Long id) {
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setStatus(0);
        return milktypeRepository.save(milktype1);
    }

    @Override
    public Optional<MilkType> getMilkTypeByName(String milkTypeName) {
        return milktypeRepository.findByMilkTypename(milkTypeName);
    }

    @Override
    public List<MilkType> GetAllMilktype() {
        return milktypeRepository.findAll();
    }


}
