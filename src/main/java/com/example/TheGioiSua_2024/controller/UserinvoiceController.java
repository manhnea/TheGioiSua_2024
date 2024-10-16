package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.service.UserinvoiceService;
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
@RequestMapping("/Userinvoice")
public class UserinvoiceController {
    @Autowired
    private UserinvoiceService userinvoiceService;

    //RessourceEndPoint:http://localhost:1234/api/Userinvoice/lst
    @GetMapping("/lst")
    public List<Userinvoice> listUserinvoice() {
        return userinvoiceService.getUserinvoiceList();
    }

//    http://localhost:1234/Userinvoice/add
    @PostMapping("/add")
    public ResponseEntity<?> addUserinvoice(@RequestBody @Valid Userinvoice userinvoice, BindingResult bindingResult) {
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
        return ResponseEntity.ok(Map.of("status", "success", "message", userinvoiceService.saveUserinvoice(userinvoice)));
    }

    //RessourceEndPoint:http://localhost:1234/api/Userinvoice/update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserinvoice(@PathVariable Long id, @RequestBody @Valid Userinvoice userinvoice, BindingResult bindingResult) {
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
        return ResponseEntity.ok(Map.of("status", "success", "message", userinvoiceService.updateUserinvoice(id, userinvoice)));
    }

    //RessourceEndPoint:http://localhost:1234/api/Userinvoice/delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserinvoice(@PathVariable Long id, @RequestBody Userinvoice userinvoice) {
        userinvoiceService.deleteUserinvoice(id, userinvoice);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Xóa thành công"));
    }

}
