package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ilogService {


  List<Log> getAll();

  List<Log> getLogByUsername(String username);
}
