package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Usermanagement;
import com.example.TheGioiSua_2024.service.UsermanagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Usermanagement")
public class UsermanagementRestController {
    @Autowired
    private UsermanagementService _usermanagementService;

    @GetMapping("/list")
    public List<Usermanagement> getAllUsermanagement(){
        return _usermanagementService.getAllUsermanagement();
    }

    @PostMapping("/add")
    public Usermanagement addUsermanagement(@RequestBody Usermanagement usermanagement ){

        return _usermanagementService.addUsermanagement(usermanagement);
    }

    @PutMapping("/update/{id}")
    public Usermanagement updateUsermanagement(@PathVariable("id") Long id,@RequestBody Usermanagement usermanagement){
        return _usermanagementService.updateUsermanagement(id, usermanagement);
    }

    @PutMapping("/delete/{id}")
    public Usermanagement deleteUsermanagement(@PathVariable("id") Long id){
        return _usermanagementService.deleteUsermanagement(id);
    }
}
