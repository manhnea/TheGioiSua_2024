package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.repository.UsagecapacityRepository;
import com.example.TheGioiSua_2024.service.impl.IUsagecapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsagecapacityService implements IUsagecapacityService {
    @Autowired
    UsagecapacityRepository usagecapacityRepository;
    @Override
    public Usagecapacity addUsagecapacity(Usagecapacity usagecapacity) {
        usagecapacity.setStatus(1);
        return usagecapacityRepository.save(usagecapacity);
    }

    @Override
    public List<Usagecapacity> getAllUsagecapacity() {
        return usagecapacityRepository.findAll();
    }

    @Override
    public Usagecapacity updateUsagecapacity(Long id, Usagecapacity usagecapacity) {
        Usagecapacity  usagecapacity1 = usagecapacityRepository.findById(id).orElseThrow();
        usagecapacity1.setCapacity(usagecapacity.getCapacity());
        usagecapacity1.setUnit(usagecapacity.getUnit());
        return usagecapacityRepository.save(usagecapacity1);
    }

    @Override
    public Usagecapacity deleteUsagecapacity(Long id) {
        Usagecapacity  usagecapacity1 = usagecapacityRepository.findById(id).orElseThrow();
        usagecapacity1.setStatus(0);
        return usagecapacityRepository.save(usagecapacity1);
    }
}
