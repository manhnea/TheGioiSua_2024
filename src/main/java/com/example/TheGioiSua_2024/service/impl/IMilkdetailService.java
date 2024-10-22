package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMilkdetailService {
    List<Milkdetail> getAll();

    String add(Milkdetail milkdetail);

    String update(Long id, Milkdetail milkdetail);

    String delete(Long id);

    Milkdetail getById(Long id);
    
    Page<MilkDetailDto> getPageMilkDetail(Pageable pageable);
}
