package com.example.TheGioiSua_2024.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ITotalStatisticsService {

  List<Object[]> getSalesRevenue(String voucher, LocalDateTime startDate, LocalDateTime endDate);

  List<Object[]> getRevenueByDate(Integer voucherId, LocalDateTime startDate,
    LocalDateTime endDate);
}

