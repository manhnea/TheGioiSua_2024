package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.service.impl.IMilktypeService;
import com.example.TheGioiSua_2024.util.Status;
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
        Optional<MilkType> existingContainer = getMilkTypeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên này đã tồn tại.";
        }
        milktype.setStatus(Status.Active);
        milktypeRepository.save(milktype);
        return "Them Thanh Cong";
    }
    @Override
    public String UpdateMilktype(Long id, MilkType milktype) {
        MilkType existingMilkType = milktypeRepository.findById(id).orElseThrow();
        String currentMilkTypeName = existingMilkType.getMilkTypename();
        if (currentMilkTypeName.equals(milktype.getMilkTypename())) {
            existingMilkType.setDescription(milktype.getDescription());
            existingMilkType.setStatus(Status.Active);
            milktypeRepository.save(existingMilkType);
            return "Cập nhật mô tả loại sữa sữa thành công.";
        } else if (milktypeRepository.findByMilkTypename(milktype.getMilkTypename()).isPresent()) {
            return "loại sữa sữa này đã tồn tại.";
        }
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setDescription(milktype.getDescription());
        milktype1.setMilkTypename(milktype.getMilkTypename());
         milktypeRepository.save(milktype1);
         return "Sua Thanh Cong";
    }
    @Override
    public String DeleteMilktype(Long id) {
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        if (milktype1.getStatus() == Status.Delete) {
            milktype1.setStatus(Status.Active);
            milktypeRepository.save(milktype1);
            return "Khôi phục loại sữa thành công!";
        } else {
            milktype1.setStatus(Status.Delete);
            milktypeRepository.save(milktype1);
            return "Xóa Thanh Cong";
        }
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
