package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.service.PackagingunitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Packagingunit")
public class PackagingunitRestController {
    @Autowired
    private PackagingunitService packagingunitService;
    @GetMapping("/lst")
    public List<Packagingunit> getAllPackagingunit(){
        return packagingunitService.getAllPackagingunit();
    }
    @PostMapping("/add")
    public Packagingunit addPackagingunit(@RequestBody Packagingunit packagingunit){
        return packagingunitService.addPackagingunit(packagingunit);
    }
    @PutMapping("/update/{id}")
    public Packagingunit updatePackagingunit(@PathVariable("id") Long id, @RequestBody Packagingunit packagingunit){
        return packagingunitService.updatePackagingunit(id, packagingunit);
    }
    @PutMapping("/delete/{id}")
    public Packagingunit deletePackagingunit(@PathVariable("id") Long id){
        return packagingunitService.deletePackagingunit(id);
    }
}
