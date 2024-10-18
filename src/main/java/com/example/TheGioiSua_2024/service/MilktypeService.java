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
        String trimmedName = milktype.getMilkTypename().trim();
        milktype.setMilkTypename(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<MilkType> existingContainer = getMilkTypeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên này đã tồn tại.";
        }
        milktype.setStatus(1);
        milktypeRepository.save(milktype);
        return "Them Thanh Cong";
    }

    @Override
    public String UpdateMilktype(Long id, MilkType milktype) {
        MilkType existingMilkType = milktypeRepository.findById(id).orElseThrow();
        String currentMilkTypeName = existingMilkType.getMilkTypename();
        if (currentMilkTypeName.equals(milktype.getMilkTypename())) {
            existingMilkType.setDescription(milktype.getDescription());
            existingMilkType.setStatus(1);
            milktypeRepository.save(existingMilkType);
            return "Cập nhật mô tả thương hiệu sữa thành công.";
        } else if (milktypeRepository.findByMilkTypename(milktype.getMilkTypename()).isPresent()) {
            return "Thương hiệu sữa này đã tồn tại.";
        }
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setDescription(milktype.getDescription());
        milktype1.setMilkTypename(milktype.getMilkTypename());
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
    public Optional<MilkType> getMilkTypeByName(String milktypename) {
        return milktypeRepository.findByMilkTypename(milktypename);
    }

    @Override
    public MilkType GetMilktypeById(Long id) {
        return milktypeRepository.findById(id).orElseThrow();
    }


    @Override
    public List<MilkType> GetAllMilktype() {
        return milktypeRepository.findAll();
    }


}
