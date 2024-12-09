package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Log;

import com.example.TheGioiSua_2024.repository.LogRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;

import com.example.TheGioiSua_2024.service.impl.ilogService;
import jakarta.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.TheGioiSua_2024.entity.Log;

@RestController
@RequestMapping("/Log")
public class LogController {

  @Autowired
  private ilogService ilogService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LogRepository logRepository;
  @Autowired
  private JwtUtilities jwtUtilities;

  @GetMapping("/lst")
  public ResponseEntity<Page<Log>> lst(
      @RequestParam(required = false) String username,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);

    if (username != null && !username.isEmpty()) {
      return ResponseEntity.ok(ilogService.searchLogByUsername(username, pageable));
    }
    return ResponseEntity.ok(ilogService.getAll(pageable));
  }

  // Lấy logs của user từ token
//  http://localhost:1234/api/Log/getlog
  @GetMapping("/getlog")
  public ResponseEntity<Page<Log>> getLogByUsername(
      @NonNull HttpServletRequest request,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    String token = jwtUtilities.getToken(request);
    String username = jwtUtilities.extractUsername(token);
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(ilogService.getLogByUsername(username, pageable));
  }

  // Tìm logs theo username
  @GetMapping("/search")
  public ResponseEntity<Page<Log>> searchLogByUsername(
      @RequestParam String username,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(ilogService.searchLogByUsername(username, pageable));
  }
}
