package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.service.MilkdetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
    private String add(@RequestBody @Valid Milkdetail milkdetail, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }

       String mess = milkdetailService.add(milkdetail);
        return mess;
    }
    //http://localhost:1234/api/Milkdetail/update/{id}
    @PutMapping("/update/{id}")
    private String update(@PathVariable("id") Long id,  @Valid @RequestBody Milkdetail milkdetail, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return milkdetailService.update(id, milkdetail);
    }
    //http://localhost:1234/api/Milkdetail/delete/{id}
    @PutMapping("/delete/{id}")
    private String delete(@PathVariable("id") Long id, @RequestBody Milkdetail milkdetail){
        String mess = milkdetailService.delete(id, milkdetail);
        return mess;
    }

}
