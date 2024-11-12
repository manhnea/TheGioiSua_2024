/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service.impl;


import com.example.TheGioiSua_2024.dto.ForgotPasswordDto;
import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;


public interface IUserService {
  //ResponseEntity<?> register (RegisterDto registerDto);
  //  ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

  ResponseEntity<?> authenticate(LoginDto loginDto);

  ResponseEntity<?> verifyAccount(String token);

  ResponseEntity<?> register(RegisterDto registerDto);

  Role saveRole(Role role);

  User saverUser(User user);

  UserDto findUserById(Long id);

  ResponseEntity<?> forgotPassword(ForgotPasswordDto forgotPasswordDto);

  ResponseEntity<?> resetPassword(String token, String newPassword);

  ResponseEntity<?> changePassword(Long userId, String oldPassword, String newPassword);

  List<String> findTop5();
}