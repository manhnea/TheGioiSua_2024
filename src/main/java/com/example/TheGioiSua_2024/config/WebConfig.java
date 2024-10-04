///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.example.TheGioiSua_2024.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Cho phép tất cả các endpoint
//                .allowedOrigins("http://127.0.0.1:5500") // Thay thế bằng nguồn gốc của bạn
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Cho phép các phương thức
//                .allowedHeaders("*") // Cho phép tất cả các header
//                .allowCredentials(true); // Cho phép gửi thông tin xác thực (nếu cần)
//    }
//}
//
//
