package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.service.MilktypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
    //http://localhost:1234/api/Milktype/add
    @PostMapping("/add")
    public String addMilktype(@RequestBody @Valid MilkType milktype, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
         milktypeService.AddMilktype(milktype);
        return "The milktype has been added successfully";
    }//http://localhost:1234/api/Milktype/update/{id}
    @PutMapping("/update/{id}")
    public String updateMilktype(@PathVariable("id") Long id, @RequestBody @Valid MilkType milktype,BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
         milktypeService.UpdateMilktype(id, milktype);
        return "The milktype has been updated successfully";
    }//http://localhost:1234/api/Milktype/delete/{id}
    @PutMapping("/delete/{id}")
    public MilkType deleteMilktype(@PathVariable("id") Long id) {
        return milktypeService.DeleteMilktype(id);
    }
}
