package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.service.MilkbrandService;
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
@RequestMapping("/Milkbrand")
public class MilkbrandController {
    @Autowired
    private MilkbrandService milkbrandService;
    //http://localhost:1234/api/Milkbrand/lst
    @GetMapping("/lst")
    public List<Milkbrand> lst() {
        return milkbrandService.getAllMilkbrands();
    }

    @GetMapping("/lst/{id}")
    public Milkbrand getMilkbrandById(@PathVariable Long id) {
        return milkbrandService.getMilkbrandById(id);
    }

    //http://localhost:1234/api/Milkbrand/add
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Milkbrand milkbrand, BindingResult bindingResult) {
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

        String resultMessage = milkbrandService.addMilkbrand(milkbrand);
        return ResponseEntity.ok(Map.of("status", "success", "message", resultMessage));
    }

    //http://localhost:1234/api/Milkbrand/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Milkbrand milkbrand , BindingResult bindingResult) {
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
        return ResponseEntity.ok(Map.of("status", "success", "message", milkbrandService.updateMilkbrand(id, milkbrand)));
    }
    //http://localhost:1234/api/Milkbrand/delete/{id}
    @DeleteMapping("/delete/{id}") // Change to DELETE method
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        String message = milkbrandService.deleteMilkbrand(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", message));
    }
}
