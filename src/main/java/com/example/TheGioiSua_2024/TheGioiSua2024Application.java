package com.example.TheGioiSua_2024;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class TheGioiSua2024Application {

  public static void main(String[] args) {
    SpringApplication.run(TheGioiSua2024Application.class, args);
  }

//  @Scheduled(fixedRate = 2000)
//  public void executeTask() {
//    System.out.println("Hàm này được gọi tự động theo khoảng thời gian 2 giây!");
//  }
}
