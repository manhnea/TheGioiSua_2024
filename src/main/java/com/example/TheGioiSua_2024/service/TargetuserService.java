package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
import com.example.TheGioiSua_2024.service.impl.ITargetuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TargetuserService implements ITargetuserService {
    @Autowired
    private TargetuserRepository targetuserRepository;
    @Override
    public List<Targetuser> getAllTargetuser() {
        return targetuserRepository.findAll();
    }

    @Override
    public Targetuser addTargetuser(Targetuser targetuser) {
        targetuser.setStatus(1);
        return targetuserRepository.save(targetuser);
    }

    @Override
    public Targetuser updateTargetuser(Long id, Targetuser targetuser) {
        Targetuser targetuser1 = targetuserRepository.findById(id).orElseThrow();
        targetuser1.setDescription(targetuser.getDescription());
        targetuser1.setTargetname(targetuser.getTargetname());
        return targetuserRepository.save(targetuser1);
    }

    @Override
    public Targetuser deleteTargetuser(Long id) {
        Targetuser targetuser1 = targetuserRepository.findById(id).orElseThrow();
        targetuser1.setStatus(0);
        return targetuserRepository.save(targetuser1);
    }
}
