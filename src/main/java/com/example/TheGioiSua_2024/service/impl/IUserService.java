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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface IUserService {
  //ResponseEntity<?> register (RegisterDto registerDto);
  //  ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

  ResponseEntity<?> authenticate(LoginDto loginDto);

  ResponseEntity<?> verifyAccount(String token);

  ResponseEntity<?> register(RegisterDto registerDto);

  Role saveRole(Role role);

  User saverUser(User user);

  ResponseEntity<?> updatePhoneNumber(String token, User user);

  ResponseEntity<?> updateAddress(String token, User user);

  UserDto findUserById(Long id);

  ResponseEntity<?> forgotPassword(ForgotPasswordDto forgotPasswordDto);

  ResponseEntity<?> resetPassword(String token, String newPassword);

  ResponseEntity<?> changePassword(String token, String oldPassword,
    String newPassword);

  long countUsersByRoleAndStatus();

  List<String> findTop5();

  List<User> getAllUsers();
  
  Page<User> getUserPage(Pageable pageable);

  Page<User> getCustomerPage(Pageable pageable);
  
  User updateUser(Long id, User user);

  String deleteUser(Long id);

  User getbyID(Long id);

  Object updateFullName(String token, User user);
}
