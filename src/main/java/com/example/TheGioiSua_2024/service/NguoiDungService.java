///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package com.example.TheGioiSua_2024.service;

/**
 *
 * @author Administrator
 */
import com.example.TheGioiSua_2024.model.CustomUserDetails;
import com.example.TheGioiSua_2024.model.NguoiDung;
import com.example.TheGioiSua_2024.repository.NguoiDungRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NguoiDungService implements UserDetailsService {

    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.timKiemVoiTenDangNhap(username);
        if (nguoiDung != null) {
            return new CustomUserDetails(nguoiDung);
        }
        throw new UsernameNotFoundException("Người dùng không tồn tại: " + username);
    }
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.timKiemVoiId(id);
        if (nguoiDung != null) {
            return new CustomUserDetails(nguoiDung);
        }
        throw new UsernameNotFoundException("Người dùng không tồn tại: " + id);
    }
}
