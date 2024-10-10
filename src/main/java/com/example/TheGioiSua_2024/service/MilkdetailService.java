package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilkdetailService implements IMilkdetailService {
    @Autowired
    private MilkdetailRepository milkdetailRepository;


    @Override
    public List<Milkdetail> getAll() {
        return milkdetailRepository.findAll();
    }

    @Override
    public Milkdetail add(Milkdetail milkdetail) {
        return null;
    }

    @Override
    public Milkdetail update(Long id, Milkdetail milkdetail) {
        return null;
    }

    @Override
    public Milkdetail delete(Long id, Milkdetail milkdetail) {
        return null;
    }

}
