package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Setting;
import com.example.TheGioiSua_2024.repository.SettingRepository;
import com.example.TheGioiSua_2024.service.impl.ISettingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.CacheRequest;

@Service
public class Settingservice implements ISettingservice {
    @Autowired
    private SettingRepository settingRepository;
    @Override
    public ResponseEntity<Setting> getSettings() {
        return ResponseEntity.ok(settingRepository.findAll().get(0));
    }

    @Override
    public Setting updateSettings(Setting setting) {
        Long id = 1L;
        Setting existingSetting = settingRepository.findById(id).orElseThrow(() -> new RuntimeException("Setting not found"));
        if (setting.getAddress() != null) {
            existingSetting.setAddress(setting.getAddress());
        }
        if (setting.getHotline() != null) {
            existingSetting.setHotline(setting.getHotline());
        }
        if (setting.getEmail() != null) {
            existingSetting.setEmail(setting.getEmail());
        }
        if (setting.getFullname() != null) {
            existingSetting.setFullname(setting.getFullname());
        }
        if (setting.getNameshop() != null) {
            existingSetting.setNameshop(setting.getNameshop());
        }
        if (setting.getLogo() != null) {
            existingSetting.setLogo(setting.getLogo());
        }
        if (setting.getApikey() != null) {
            existingSetting.setApikey(setting.getApikey());
        }
        if (setting.getValuebank() != null) {
            existingSetting.setValuebank(setting.getValuebank());
        }
        if (setting.getStk() != null) {
            existingSetting.setStk(setting.getStk());
        }

        return settingRepository.save(existingSetting);
    }


}
