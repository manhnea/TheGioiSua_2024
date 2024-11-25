package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ilogService {

  Page<Log> getAll(Pageable pageable);

  Page<Log> getLogByUsername(String username, Pageable pageable);

  Page<Log> searchLogByUsername(String username, Pageable pageable);
}
