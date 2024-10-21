package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.service.PackagingunitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Packagingunit")
public class PackagingunitRestController {
    @Autowired
    private PackagingunitService packagingunitService;

    //http://localhost:1234/api/Packagingunit/lst
    @GetMapping("/lst")
    public List<Packagingunit> getAllPackagingunit(){
        return packagingunitService.getAllPackagingunit();
    }
    @GetMapping("/lst/{id}")
    public Packagingunit getPackagingunit(@PathVariable("id") Long id){
        return packagingunitService.getPackagingunitById(id);
    }
    //http://localhost:1234/api/Packagingunit/add
    @PostMapping("/add")
    public ResponseEntity<?> addPackagingunit(@RequestBody @Valid Packagingunit packagingunit, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                Map<String, String> error = new HashMap<>();
                error.put("field", fieldError.getField());
                error.put("message", fieldError.getDefaultMessage());
                errors.add(error);
            }
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
        }
       return ResponseEntity.ok(Map.of("status", "success", "message", packagingunitService.addPackagingunit(packagingunit)));
    }
    //http://localhost:1234/api/Packagingunit/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePackagingunit(@PathVariable("id") Long id, @RequestBody @Valid Packagingunit packagingunit, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                Map<String, String> error = new HashMap<>();
                error.put("field", fieldError.getField());
                error.put("message", fieldError.getDefaultMessage());
                errors.add(error);
            }
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
        }
        return ResponseEntity.ok(Map.of("status", "success", "message", packagingunitService.updatePackagingunit(id, packagingunit)));
    }
    //http://localhost:1234/api/Packagingunit/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePackagingunit(@PathVariable("id") Long id){
        String message = packagingunitService.deletePackagingunit(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", message));
    }
}
