package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.service.MilkbrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Milkbrand")
public class MilkbrandController {
    @Autowired
    private MilkbrandService milkbrandService;
    @GetMapping("/lst")
    public List<Milkbrand> lst() {
        return milkbrandService.getAllMilkbrands();
    }

    @PostMapping("/add")
    public String add(@RequestBody @Valid Milkbrand milkbrand, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
       String mess = milkbrandService.addMilkbrand(milkbrand);
        return mess;
    }
    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestBody @Valid Milkbrand milkbrand , BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        String mess = milkbrandService.updateMilkbrand(id, milkbrand);
        return mess;
    }
    @PutMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @RequestBody Milkbrand milkbrand) {
        milkbrandService.deleteMilkbrand(id, milkbrand);
        return "Xóa thành công";
    }
}
