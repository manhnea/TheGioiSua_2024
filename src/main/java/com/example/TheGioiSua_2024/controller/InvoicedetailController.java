package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.service.InvoicedetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    http://localhost:1234/Invoicedetail/add
    @PostMapping("/add")
    public String saveInvoicedetail(@RequestBody @Valid Invoicedetail invoicedetail, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return invoicedetailService.saveInvoicedetail(invoicedetail);
    }


    @PutMapping("/update/{id}")
    public String updateInvoicedetail(@PathVariable Long id, @RequestBody @Valid Invoicedetail invoicedetail, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return invoicedetailService.updateInvoicedetail(id, invoicedetail);
    }


//    http://localhost:1234/Invoicedetail/delete/1
    @PutMapping("/delete/{id}")
    public void deleteInvoicedetail(@PathVariable Long id, @RequestBody Invoicedetail invoicedetail) {
        invoicedetailService.deleteInvoicedetail(id, invoicedetail);
    }
}
