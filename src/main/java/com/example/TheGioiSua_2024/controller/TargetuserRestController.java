package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.service.TargetuserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Targetuser")
public class TargetuserRestController {
    @Autowired
    private TargetuserService targetuserService;
    @GetMapping("/lst")
    public List<Targetuser> lst() {
        return targetuserService.getAllTargetuser();
    }
    @PostMapping("/add")
    public String add(@RequestBody @Valid Targetuser targetuser, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return targetuserService.addTargetuser(targetuser);
    }
    @PutMapping("/update/{id}")
    public String update(@RequestBody @Valid Targetuser targetuser,BindingResult bindingResult, @PathVariable("id") Long id) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return targetuserService.updateTargetuser(id, targetuser);
    }
    @PutMapping("delete/{id}")
    public Targetuser delete(@PathVariable("id") Long id) {
        return targetuserService.deleteTargetuser(id);
    }
}
