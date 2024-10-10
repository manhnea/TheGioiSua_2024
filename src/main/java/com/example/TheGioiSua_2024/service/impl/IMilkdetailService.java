package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IMilkdetailService {
    List<Milkdetail> getAll();

    Milkdetail add(Milkdetail milkdetail);

    Milkdetail update(Long id, Milkdetail milkdetail);

    Milkdetail delete(Long id, Milkdetail milkdetail);
}
