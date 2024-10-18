package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.service.UsagecapacityService;
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
@RequestMapping("/Usagecapacity")
public class UsagecapacityRestController {
    @Autowired
    private UsagecapacityService usagecapacityService;
    //http://localhost:1234/api/Usagecapacity/lst
    @GetMapping("/lst")
    public List<Usagecapacity> getUsagecapacity(){
        return usagecapacityService.getAllUsagecapacity();
    }
    //http://localhost:1234/api/Usagecapacity/add
    @PostMapping("/add")
    public ResponseEntity<?> addUsagecapacity(@RequestBody @Valid Usagecapacity usagecapacity, BindingResult bindingResult){
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
       return ResponseEntity.ok(Map.of("status", "success", "message", usagecapacityService.addUsagecapacity(usagecapacity)));
    }
    @GetMapping("/lst/{id}")
    public Usagecapacity getUsagecapacity(@PathVariable("id") Long id){
        return usagecapacityService.getUsagecapacityById(id);
    }
    //http://localhost:1234/api/Usagecapacity/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUsagecapacity(@PathVariable("id") Long id, @RequestBody @Valid Usagecapacity usagecapacity, BindingResult bindingResult){
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
        return ResponseEntity.ok(Map.of("status", "success", "message", usagecapacityService.updateUsagecapacity(id, usagecapacity)));
    }
    //http://localhost:1234/api/Usagecapacity/delete/{id}
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsagecapacity(@PathVariable("id") Long id){
        usagecapacityService.deleteUsagecapacity(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Xóa thành công"));
    }
}
