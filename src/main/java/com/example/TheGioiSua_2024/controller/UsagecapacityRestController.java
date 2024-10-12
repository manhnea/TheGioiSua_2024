package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.service.UsagecapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Usagecapacity")
public class UsagecapacityRestController {
    @Autowired
    private UsagecapacityService usagecapacityService;
    @GetMapping("/lst")
    public List<Usagecapacity> getUsagecapacity(){
        return usagecapacityService.getAllUsagecapacity();
    }
    @PostMapping("/add")
    public Usagecapacity addUsagecapacity(@RequestBody Usagecapacity usagecapacity){
       return usagecapacityService.addUsagecapacity(usagecapacity);
    }
    @PutMapping("/update/{id}")
    public Usagecapacity updateUsagecapacity(@PathVariable("id") Long id, @RequestBody Usagecapacity usagecapacity){
        return usagecapacityService.updateUsagecapacity(id, usagecapacity);
    }
    @PutMapping("/delete/{id}")
    public Usagecapacity deleteUsagecapacity(@PathVariable("id") Long id){
        return usagecapacityService.deleteUsagecapacity(id);
    }
}
