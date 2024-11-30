package com.example.TheGioiSua_2024.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ITotalStatisticsService {

  List<Object[]> getSalesRevenue(Long voucherId, LocalDateTime startDate, LocalDateTime endDate,
    Integer status);
}

