package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Size;
import com.example.TheGioiSua_2024.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Size")
public class SizeRestController {
    @Autowired
    private SizeService sizeService;
    @GetMapping("/lst")
    public List<Size> lst() {
        return sizeService.findAll();
    }
    @PostMapping("/add")
    public Size add(@RequestBody Size size) {
        return sizeService.save(size);
    }
    @PutMapping("/update/{id}")
    public Size update(@PathVariable("id") Long id, @RequestBody Size size) {
        return sizeService.updateSizeById(id,size);
    }
    @PutMapping("/delete/{id}")
    public Size delete(@PathVariable("id") Long id) {
        return sizeService.deleteSizeById(id);
    }
}
