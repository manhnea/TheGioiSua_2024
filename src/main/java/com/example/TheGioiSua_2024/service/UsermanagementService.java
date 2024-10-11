package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Usermanagement;
import com.example.TheGioiSua_2024.repository.UsermanagementRepository;
import com.example.TheGioiSua_2024.service.impl.IUsermanagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsermanagementService implements IUsermanagementService {

    @Autowired
    private UsermanagementRepository _usermanagementRepository;

    @Override
    public List<Usermanagement> getAllUsermanagement() {
        return _usermanagementRepository.findAll();
    }

    @Override
    public Usermanagement getUsermanagementById(Long id) {
        Usermanagement user = _usermanagementRepository.findById(id).orElseThrow();
        return user;
    }

    @Override
    public Usermanagement addUsermanagement(Usermanagement usermanagement) {
        usermanagement.setStatus(1);
        return _usermanagementRepository.save(usermanagement);
    }

    @Override
    public Usermanagement updateUsermanagement(Long id, Usermanagement usermanagement) {

        Usermanagement user = _usermanagementRepository.findById(id).orElseThrow();
        user.setUsername(usermanagement.getUsername());
        user.setPassword(usermanagement.getPassword());
        user.setRole(usermanagement.getRole());
        user.setFullname(usermanagement.getFullname());
        user.setRegistrationdate(usermanagement.getRegistrationdate());
        user.setPhonenumber(usermanagement.getPhonenumber());
        user.setEmail(usermanagement.getEmail());
        user.setAddress(usermanagement.getAddress());

        return _usermanagementRepository.save(user);
    }

    @Override
    public Usermanagement deleteUsermanagement(Long id) {
        Usermanagement user = _usermanagementRepository.findById(id).orElseThrow();
        user.setStatus(0);
        return _usermanagementRepository.save(user);
    }
}
