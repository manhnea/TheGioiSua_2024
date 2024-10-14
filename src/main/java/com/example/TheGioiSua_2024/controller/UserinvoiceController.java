package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.service.UserinvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String addUserinvoice(@RequestBody @Valid Userinvoice userinvoice, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return userinvoiceService.saveUserinvoice(userinvoice);
    }

    //RessourceEndPoint:http://localhost:1234/api/Userinvoice/update
    @PutMapping("/update/{id}")
    public String updateUserinvoice(@PathVariable Long id, @RequestBody @Valid Userinvoice userinvoice, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return userinvoiceService.updateUserinvoice(id, userinvoice);
    }

    //RessourceEndPoint:http://localhost:1234/api/Userinvoice/delete
    @DeleteMapping("/delete/{id}")
    public String deleteUserinvoice(@PathVariable Long id, @RequestBody Userinvoice userinvoice) {
        userinvoiceService.deleteUserinvoice(id, userinvoice);
        return "Userinvoice deleted successfully";
    }

}
