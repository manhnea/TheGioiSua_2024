package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.service.MilktasteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Milktaste")
public class MilktasteRestController {
    @Autowired
    private MilktasteService milktasteService;
    @GetMapping("/lst")
    public List<Milktaste> lst() {
        return milktasteService.getAllMilktaste();
    }
    @PostMapping("/add")
    public String add(@RequestBody @Valid Milktaste milktaste, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return milktasteService.addMilktaste(milktaste);
    }
    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid Milktaste milktaste, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return milktasteService.updateMilktaste(id, milktaste);
    }
    @PutMapping("/delete/{id}")
    public Milktaste delete(@PathVariable("id") Long id) {
        return milktasteService.deleteMilktaste(id);
    }

}
