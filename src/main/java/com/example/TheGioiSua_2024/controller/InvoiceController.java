package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String addInvoice(@RequestBody @Valid Invoice invoice, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        String result = invoiceService.saveInvoice(invoice);
        return result;
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/update
    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable Long id,@RequestBody @Valid Invoice invoice, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        String result = invoiceService.updateInvoice(id, invoice);
        return result;
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/delete
    @DeleteMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id ,@RequestBody Invoice invoice) {
        invoiceService.deleteInvoice(id, invoice);
        return "Invoice deleted successfully";
    }
}
