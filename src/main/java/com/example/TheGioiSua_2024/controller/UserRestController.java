/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.ForgotPasswordDto;
import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.service.UserService;
import com.example.TheGioiSua_2024.service.impl.IUserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

  private final IUserService iUserService;
  private final UserService userService;

  @GetMapping("/verify")
  public ResponseEntity<?> verifyAccount(@RequestParam("token") String token) {
    ResponseEntity<?> response = userService.verifyAccount(token);
    return response;
  }

  //RessourceEndPoint:http://localhost:1234/api/user/register
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
    return iUserService.register(registerDto);
  }

  //RessourceEndPoint:http://localhost:1234/api/user/authenticate
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
    return iUserService.authenticate(loginDto);
  }

  //http://localhost:1234/api/user/id
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    UserDto userDto = iUserService.findUserById(id);
    return ResponseEntity.ok(userDto);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
    return iUserService.forgotPassword(forgotPasswordDto);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
      @RequestParam("newPassword") String newPassword) {
    return iUserService.resetPassword(token, newPassword);
  }

  @PostMapping("/change-password")
  public ResponseEntity<?> changePassword(
      @RequestParam("userId") Long userId,
      @RequestParam("oldPassword") String oldPassword,
      @RequestParam("newPassword") String newPassword) {
    return iUserService.changePassword(userId, oldPassword, newPassword);
  }

  @PutMapping("/updatePhonerNumber")
  public ResponseEntity<?> updatePhonerNumber(@RequestBody @Valid User user,
      BindingResult bindingResult) {
    if (bindingResult.hasFieldErrors("phonenumber")) { // Kiểm tra lỗi chỉ với trường phoneNumber
      List<Map<String, String>> errors = new ArrayList<>();
      for (FieldError fieldError : bindingResult.getFieldErrors("phonenumber")) {
        Map<String, String> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());
        errors.add(error);
      }
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }
    return ResponseEntity.ok(userService.updatePhoneNumber(user));
  }

  @PutMapping("/updateAddress")
  public ResponseEntity<?> updateAddress(@RequestBody @Valid User user,
      BindingResult bindingResult) {
    if (bindingResult.hasFieldErrors("address")) { // Kiểm tra lỗi chỉ với trường phoneNumber
      List<Map<String, String>> errors = new ArrayList<>();
      for (FieldError fieldError : bindingResult.getFieldErrors("address")) {
        Map<String, String> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());
        errors.add(error);
      }
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }
    return ResponseEntity.ok(userService.updateAddress(user));
  }

  @GetMapping("/findTop5")
  public List<String> findTop5() {
    return iUserService.findTop5();
  }

  @GetMapping("/count")
  public long countUsersByRoleAndStatus() {
    return iUserService.countUsersByRoleAndStatus();
  }

}
