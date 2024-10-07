package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.service.MilktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Milktype")
public class MilktypeRestController {
    @Autowired
    private MilktypeService milktypeService;
    @GetMapping("/lst")
    public List<MilkType> getAllMilktype() {
        return milktypeService.GetAllMilktype();
    }
    @PostMapping("/add")
    public MilkType addMilktype(@RequestBody MilkType milktype) {
        return milktypeService.AddMilktype(milktype);
    }
    @PutMapping("/update/{id}")
    public MilkType updateMilktype(@PathVariable("id") Long id, @RequestBody MilkType milktype) {
        return milktypeService.UpdateMilktype(id, milktype);
    }
    @PutMapping("/delete/{id}")
    public MilkType deleteMilktype(@PathVariable("id") Long id) {
        return milktypeService.DeleteMilktype(id);
    }
}
