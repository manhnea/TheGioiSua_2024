package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IMilkdetailService {
    List<Milkdetail> getAll();

    String add(Milkdetail milkdetail);

    String update(Long id, Milkdetail milkdetail);

    String delete(Long id, Milkdetail milkdetail);

    Milkdetail getById(Long id);
}
