/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.ForgotPasswordDto;
import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.dto.UserOnlineDto;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.UserService;
import com.example.TheGioiSua_2024.service.impl.IUserService;
import com.example.TheGioiSua_2024.util.SessionUserLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

  private final IUserService iUserService;
  private final UserService userService;
  private final JwtUtilities jwtUtilities;

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

  //RessourceEndPoint:http://localhost:1234/api/user/profile/{id}
  @GetMapping("/profile/{id}")
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
  public ResponseEntity<?> changePassword(@NonNull HttpServletRequest request,
    @RequestParam("oldPassword") String oldPassword,
    @RequestParam("newPassword") String newPassword) {
    String token = jwtUtilities.getToken(request);
    return iUserService.changePassword(token, oldPassword, newPassword);
  }

  @PutMapping("/updatePhonerNumber")
  public ResponseEntity<?> updatePhonerNumber(@NonNull HttpServletRequest request,
    @RequestBody @Valid User user,
    BindingResult bindingResult) {
    // Kiểm tra nếu phonenumber là null
    if (user.getPhonenumber() == null || user.getPhonenumber().trim().isEmpty()) {
      List<Map<String, String>> errors = new ArrayList<>();
      Map<String, String> error = new HashMap<>();
      error.put("field", "phonenumber");
      error.put("message", "Số điện thoại không được để trống");
      errors.add(error);

      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }

    // Kiểm tra lỗi với trường phonenumber
    if (bindingResult.hasFieldErrors("phonenumber")) {
      List<Map<String, String>> errors = new ArrayList<>();
      for (FieldError fieldError : bindingResult.getFieldErrors("phonenumber")) {
        Map<String, String> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());
        errors.add(error);
      }

      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }

    // Nếu không có lỗi, tiếp tục xử lý cập nhật số điện thoại
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(userService.updatePhoneNumber(token, user));
  }

  @PutMapping("/updateFullName")
  public ResponseEntity<?> updateFullName(@NonNull HttpServletRequest request,
    @RequestBody @Valid User user,
    BindingResult bindingResult) {
    String token = jwtUtilities.getToken(request);
    if (bindingResult.hasFieldErrors("fullname")) { // Kiểm tra lỗi chỉ với trường phoneNumber
      List<Map<String, String>> errors = new ArrayList<>();
      for (FieldError fieldError : bindingResult.getFieldErrors("fullname")) {
        Map<String, String> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());
        errors.add(error);
      }
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }

    return ResponseEntity.ok(userService.updateFullName(token, user));
  }

  @PutMapping("/updateAddress")
  public ResponseEntity<?> updateAddress(@NonNull HttpServletRequest request,
    @RequestBody @Valid User user,
    BindingResult bindingResult) {
    String token = jwtUtilities.getToken(request);
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

    return ResponseEntity.ok(userService.updateAddress(token, user));
  }

  @GetMapping("/findTop5")
  public List<String> findTop5() {
    return iUserService.findTop5();
  }

  @GetMapping("/count")
  public long countUsersByRoleAndStatus() {
    return iUserService.countUsersByRoleAndStatus();
  }

  @GetMapping("lst")
  public List<User> getAllUsers() {
    return iUserService.getAllUsers();
  }

  @PutMapping("/update/{id}")
  public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    return iUserService.updateUser(id, user);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    String message = iUserService.deleteUser(id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  @GetMapping("/lst/{id}")
  public User getUsersByRole(@PathVariable("id") Long id) {
    return iUserService.getbyID(id);
  }

  @GetMapping("/online")
  public Set<UserOnlineDto> onlineUsers() {
    return SessionUserLogin.getOnline();
  }
  
  @GetMapping("/Userpage")
  public Page<User> getUserPage(@RequestParam("page") int page, @RequestParam("size") int size, Pageable pageable ){
      Pageable pageable1 = PageRequest.of(page, size);
      return iUserService.getUserPage(pageable);
  }
  
  @GetMapping("/Customerpage")
  public Page<User> getCustomerPage(@RequestParam("page") int page, @RequestParam("size") int size, Pageable pageable ){
      Pageable pageable1 = PageRequest.of(page, size);
      return iUserService.getCustomerPage(pageable);
  }
          
}
