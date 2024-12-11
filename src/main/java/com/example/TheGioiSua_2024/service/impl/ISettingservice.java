package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Setting;
import org.springframework.http.ResponseEntity;

import java.net.CacheRequest;

public interface ISettingservice {
    ResponseEntity<Setting> getSettings();
    Setting updateSettings(Setting setting);
}
