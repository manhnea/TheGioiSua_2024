package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.service.MilkdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/milkdetail")
public class MilkdetailRestController {
    @Autowired
    private MilkdetailService milkdetailService;

    @GetMapping("/lst")
    private List<Milkdetail> lst(){
        return milkdetailService.getAll();
    }
    @PostMapping("/add")
    private Milkdetail add(@RequestBody Milkdetail milkdetail){
        return milkdetailService.add(milkdetail);
    }
    @PutMapping("/update/{id}")
    private Milkdetail update(@PathVariable("id") Long id, @RequestBody Milkdetail milkdetail){
        return milkdetailService.update(id,milkdetail);
    }
    @PutMapping("/delete/{id}")
    private Milkdetail delete(@PathVariable("id") Long id, @RequestBody Milkdetail milkdetail){
        return milkdetailService.delete(id,milkdetail);
    }
}
