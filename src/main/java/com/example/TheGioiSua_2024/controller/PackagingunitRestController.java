package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.service.PackagingunitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
    public String addPackagingunit(@RequestBody @Valid Packagingunit packagingunit, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return packagingunitService.addPackagingunit(packagingunit);
    }
    @PutMapping("/update/{id}")
    public String updatePackagingunit(@PathVariable("id") Long id, @RequestBody @Valid Packagingunit packagingunit, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return packagingunitService.updatePackagingunit(id, packagingunit);
    }
    @PutMapping("/delete/{id}")
    public Packagingunit deletePackagingunit(@PathVariable("id") Long id){
        return packagingunitService.deletePackagingunit(id);
    }
}
