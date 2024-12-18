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
import org.springframework.web.server.ResponseStatusException;

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
  public ResponseEntity<?> updatePhoneNumber(@NonNull HttpServletRequest request, @RequestBody User user) {
      String token = jwtUtilities.getToken(request);
      return ResponseEntity.ok(userService.updatePhoneNumber(token, user));
    }


  @PutMapping("/updateFullName")
  public ResponseEntity<?> updateFullName(@NonNull HttpServletRequest request,
    @RequestBody User user) {
      String token = jwtUtilities.getToken(request);
      return ResponseEntity.ok(userService.updateFullName(token, user));
    }


  @PutMapping("/updateAddress")
  public ResponseEntity<?> updateAddress(@NonNull HttpServletRequest request,
    @RequestBody User user) {
    String token = jwtUtilities.getToken(request);
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
  public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    return iUserService.updateUser(id, user);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    return iUserService.deleteUser(id);
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

  @GetMapping("/Userpages")
  public ResponseEntity<?> getUserpages(@RequestParam("page") int page,
                                     @RequestParam("size") int size,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "fullname", required = false) String fullname,
                                     @RequestParam(value = "phonenumber", required = false) String phonenumber,
                                        @RequestParam(value = "address", required = false) String address) {
    Pageable pageable = PageRequest.of(page, size);
   Page<User> userPages = iUserService.getUserPages(pageable, username, email, fullname, phonenumber,address);
   if(userPages.isEmpty()){
       return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Không tìm thấy dữ liệu"));
   }
   return ResponseEntity.ok(Map.of("status", "success", "message", userPages));
  }
  @GetMapping("/Customerpages")
  public ResponseEntity<?> getCustomerPages(@RequestParam("page") int page,
                                     @RequestParam("size") int size,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "fullname", required = false) String fullname,
                                     @RequestParam(value = "phonenumber", required = false) String phonenumber,
                                            @RequestParam(value = "address", required = false) String address) {
    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPages = iUserService.getCustomerPages(pageable, username, email, fullname, phonenumber,address);
    if(userPages.isEmpty()){
      return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Không tìm thấy dữ liệu"));
    }
    return ResponseEntity.ok(Map.of("status", "success", "message", userPages));
  }

}
