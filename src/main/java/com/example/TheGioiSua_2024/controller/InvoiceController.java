package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.service.InvoiceService;
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
@RequestMapping("/Invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    //RessourceEndPoint:http://localhost:1234/api/Invoice/lst
    @GetMapping("/lst")
    public List<Invoice> listInvoice() {
        return invoiceService.getInvoiceList();
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/add
    @PostMapping("/add")
    public ResponseEntity<?> addInvoice(@RequestBody @Valid Invoice invoice, BindingResult bindingResult) {
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

        return ResponseEntity.ok(Map.of("status", "success", "message", invoiceService.saveInvoice(invoice)));
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/update
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Long id,@RequestBody @Valid Invoice invoice, BindingResult bindingResult) {
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
       return ResponseEntity.ok(Map.of("status", "success", "message", invoiceService.updateInvoice(id, invoice)));
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id ,@RequestBody Invoice invoice) {
        invoiceService.deleteInvoice(id, invoice);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Delete success"));
    }
}
