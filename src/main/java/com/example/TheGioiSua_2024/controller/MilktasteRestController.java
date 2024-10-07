package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.service.MilktasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Milktaste")
public class MilktasteRestController {
    @Autowired
    private MilktasteService milktasteService;
    @GetMapping("/lst")
    public List<Milktaste> lst() {
        return milktasteService.getAllMilktaste();
    }
    @PostMapping("/add")
    public Milktaste add(@RequestBody Milktaste milktaste) {
        return milktasteService.addMilktaste(milktaste);
    }
    @PutMapping("/update/{id}")
    public Milktaste update(@PathVariable("id") Long id, @RequestBody Milktaste milktaste) {
        return milktasteService.updateMilktaste(id, milktaste);
    }
    @PutMapping("/delete/{id}")
    public Milktaste delete(@PathVariable("id") Long id) {
        return milktasteService.deleteMilktaste(id);
    }
}
