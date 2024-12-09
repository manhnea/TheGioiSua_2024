package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Setting;
import com.example.TheGioiSua_2024.service.impl.ISettingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("Setting")
public class SettingsRestController {
    @Autowired
    private ISettingservice iSettingservice;

    @GetMapping("/get")
    public ResponseEntity<Setting> getSettings() {
        return ResponseEntity.ok(iSettingservice.getSettings().getBody());
    }

    @PostMapping("/update")
    public Setting updateSettings(@RequestBody Setting setting) {
        return iSettingservice.updateSettings(setting);
    }

}
