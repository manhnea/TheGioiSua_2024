package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.service.impl.IMilktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilktypeService implements IMilktypeService {
    @Autowired
    MilktypeRepository milktypeRepository;

    @Override
    public MilkType AddMilktype(MilkType milktype) {
        milktype.setStatus(1);
       return milktypeRepository.save(milktype);
    }

    @Override
    public MilkType UpdateMilktype(Long id, MilkType milktype) {
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setDescription(milktype.getDescription());
        milktype1.setMilktypename(milktype.getMilktypename());
        return milktypeRepository.save(milktype1);
    }

    @Override
    public MilkType DeleteMilktype(Long id) {
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setStatus(0);
        return milktypeRepository.save(milktype1);
    }

    @Override
    public List<MilkType> GetAllMilktype() {
        return milktypeRepository.findAll();
    }


}
