package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.service.TargetuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Targetuser")
public class TargetuserRestController {
    @Autowired
    private TargetuserService targetuserService;
    @GetMapping("/lst")
    public List<Targetuser> lst() {
        return targetuserService.getAllTargetuser();
    }
    @PostMapping("/add")
    public Targetuser add(@RequestBody Targetuser targetuser) {
        return targetuserService.addTargetuser(targetuser);
    }
    @PutMapping("/update/{id}")
    public Targetuser update(@RequestBody Targetuser targetuser, @PathVariable("id") Long id) {
        return targetuserService.updateTargetuser(id, targetuser);
    }
    @PutMapping("delete/{id}")
    public Targetuser delete(@PathVariable("id") Long id) {
        return targetuserService.deleteTargetuser(id);
    }
}
