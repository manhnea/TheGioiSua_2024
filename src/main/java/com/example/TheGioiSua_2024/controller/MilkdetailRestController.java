package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.service.MilkdetailService;
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
@RequestMapping("/Milkdetail")
public class MilkdetailRestController {
    @Autowired
    private MilkdetailService milkdetailService;
    //http://localhost:1234/api/Milkdetail/lst
    @GetMapping("/lst")
    private List<Milkdetail> lst(){
        return milkdetailService.getAll();
    }
    //http://localhost:1234/api/Milkdetail/add
    @PostMapping("/add")
    private ResponseEntity<?> add(@RequestBody @Valid Milkdetail milkdetail, BindingResult bindingResult){
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

      return ResponseEntity.ok(Map.of("status", "success", "message", milkdetailService.add(milkdetail)));
    }
    //http://localhost:1234/api/Milkdetail/update/{id}
    @PutMapping("/update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Long id,  @Valid @RequestBody Milkdetail milkdetail, BindingResult bindingResult){
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
        return ResponseEntity.ok(Map.of("status", "success", "message", milkdetailService.update(id, milkdetail)));
    }
    //http://localhost:1234/api/Milkdetail/delete/{id}
    @PutMapping("/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestBody Milkdetail milkdetail){
    milkdetailService.delete(id, milkdetail);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Xóa thành công"));
    }

}
