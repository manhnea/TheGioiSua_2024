package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Size;
import com.example.TheGioiSua_2024.service.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Size")
public class SizeRestController {
    @Autowired
    private SizeService sizeService;
    //RessourceEndPoint:http://localhost:8087/api/Size/lst
    @GetMapping("/lst")
    public List<Size> lst() {
        return sizeService.findAll();
    }
    @PostMapping("/add")
    public String add(@RequestBody @Valid Size size, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return sizeService.save(size);

    }
    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid Size size, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
         return sizeService.updateSizeById(id,size);

    }
    @PutMapping("/delete/{id}")
    public Size delete(@PathVariable("id") Long id) {
        return sizeService.deleteSizeById(id);
    }
}
