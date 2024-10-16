package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.service.VoucherService;
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
@RequestMapping("/Voucher")
public class VoucherRestController {
    @Autowired
    private VoucherService voucherService;
    //http://localhost:1234/api/Voucher/lst
    @GetMapping("/lst")
    public List<Voucher> lst(){
        return voucherService.getVoucherList();
    }
    //http://localhost:1234/api/Voucher/add
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Voucher voucher, BindingResult bindingResult){
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

        return ResponseEntity.ok(Map.of("status", "success", "message", voucherService.saveVoucher(voucher)));
    }
    //http://localhost:1234/api/Voucher/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid Voucher voucher ,BindingResult bindingResult){
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
       return ResponseEntity.ok(Map.of("status", "success", "message", voucherService.updateVoucher(id, voucher)));

    }
    //http://localhost:1234/api/Voucher/delete/{id}
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Xóa thành công"));
    }
}
