package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.service.MilktypeService;
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
@RequestMapping("/Milktype")
public class MilktypeRestController {
    @Autowired
    private MilktypeService milktypeService;
    //http://localhost:1234/api/Milktype/lst
    @GetMapping("/lst")
    public List<MilkType> getAllMilktype() {
        return milktypeService.GetAllMilktype();
    }
    @GetMapping("/lst/{id}")
    public MilkType getMilktype(@PathVariable("id") Long id) {
        return milktypeService.GetMilktypeById(id);
    }
    //http://localhost:1234/api/Milktype/add
    @PostMapping("/add")
    public ResponseEntity<?> addMilktype(@RequestBody @Valid MilkType milktype, BindingResult bindingResult) {
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
         milktypeService.AddMilktype(milktype);
        return ResponseEntity.ok(Map.of("status", "success", "message", "The milktype has been added successfully"));
    }//http://localhost:1234/api/Milktype/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMilktype(@PathVariable("id") Long id, @RequestBody @Valid MilkType milktype,BindingResult bindingResult) {
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

       return ResponseEntity.ok(Map.of("status", "success", "message", milktypeService.UpdateMilktype(id, milktype)));
    }//http://localhost:1234/api/Milktype/delete/{id}
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteMilktype(@PathVariable("id") Long id) {
        milktypeService.DeleteMilktype(id);

        return ResponseEntity.ok(Map.of("status", "success", "message", "Xóa thành công"));
    }
}
