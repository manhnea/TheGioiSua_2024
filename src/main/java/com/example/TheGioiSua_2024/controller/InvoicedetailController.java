package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.service.InvoicedetailService;
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
@RequestMapping("/Invoicedetail")
public class InvoicedetailController {
    @Autowired
    private InvoicedetailService invoicedetailService;

//    http://localhost:1234/Invoicedetail/lst
    @GetMapping("/lst")
    public List<Invoicedetail> getInvoicedetailList() {
        return invoicedetailService.getInvoicedetailList();
    }

    //    http://localhost:1234/Invoicedetail/lst/1
    @GetMapping("/lst/{id}")
    public Invoicedetail getInvoicedetailById(@PathVariable Long id) {
        return invoicedetailService.getInvoicedetailById(id);
    }
//    http://localhost:1234/Invoicedetail/add
    @PostMapping("/add")
    public ResponseEntity<?> saveInvoicedetail(@RequestBody @Valid Invoicedetail invoicedetail, BindingResult bindingResult) {
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
        return ResponseEntity.ok(Map.of("status", "success", "message", invoicedetailService.saveInvoicedetail(invoicedetail)));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvoicedetail(@PathVariable Long id, @RequestBody @Valid Invoicedetail invoicedetail, BindingResult bindingResult) {
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
        return  ResponseEntity.ok(Map.of("status", "success", "message", invoicedetailService.updateInvoicedetail(id, invoicedetail)));
    }


//    http://localhost:1234/Invoicedetail/delete/1
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoicedetail(@PathVariable Long id) {
        String message = invoicedetailService.deleteInvoicedetail(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", message));
    }

}
