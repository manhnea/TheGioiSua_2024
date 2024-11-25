package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.repository.LogRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.service.impl.ilogService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class logService implements ilogService {

  @Autowired
  private LogRepository logRepository;
  @Autowired
  private UserRepository userRepository;


  public void saveLog(String Username, Log log) {
    Optional<User> user = userRepository.findByUsername(Username);
    log.setUser(user.get());
    logRepository.save(log);
  }


  @Override
  public Page<Log> getAll(Pageable pageable) {
    return logRepository.findAll(pageable);
  }

  @Override
  public Page<Log> getLogByUsername(String username, Pageable pageable) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    return logRepository.findByUserId(user.get().getId().intValue(), pageable);
  }

  @Override
  public Page<Log> searchLogByUsername(String username, Pageable pageable) {
    return logRepository.findByUsername(username, pageable);
  }
}
