package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Container;
import com.example.TheGioiSua_2024.service.ContainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Container")
public class ContainerRestController {
    @Autowired
    private ContainerService containerService;
    @GetMapping("/lst")
    public List<Container> lst() {
        return containerService.getAllContainers();
    }
    @PostMapping("/add")
    public String add(@RequestBody @Valid Container container, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return containerService.addContainer(container);
    }
    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid Container container,BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if(fieldError != null){
                return fieldError.getDefaultMessage();
            }
        }
        return containerService.updateContainer(id, container);
    }
    @PutMapping("/delete/{id}")
    public Container delete(@PathVariable("id") Long id) {
        return containerService.deleteContainer(id);
    }
}
