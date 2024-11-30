package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.repository.UserinvoiceRepository;
import com.example.TheGioiSua_2024.service.impl.ITotalStatisticsService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TotalStatisticsService implements ITotalStatisticsService {

  @Autowired
  private UserinvoiceRepository userinvoiceRepository;

  @Override
  public List<Object[]> getSalesRevenue(Long voucherId, LocalDateTime startDate,
    LocalDateTime endDate, Integer status) {
    return userinvoiceRepository.getSalesRevenue(voucherId, startDate, endDate, status);
  }
}
