package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.repository.LogRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.service.MilkdetailService;
import com.example.TheGioiSua_2024.service.impl.ilogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Log")
public class LogController {

  @Autowired
  private ilogService ilogService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LogRepository logRepository;

  @GetMapping("/lst")
  public List<Log> lst() {
    return ilogService.getAll();
  }

  @GetMapping("/getlog/{username}")
  public ResponseEntity<List<Log>> getLogByUsername(@PathVariable String username) {
    return ResponseEntity.ok(ilogService.getLogByUsername(username));
  }
}
