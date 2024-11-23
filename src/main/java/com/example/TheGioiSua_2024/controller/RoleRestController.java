package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleRestController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/lst")
    public List<Role> getAllRole(){
        return roleService.getAllRoles();
    }
}
