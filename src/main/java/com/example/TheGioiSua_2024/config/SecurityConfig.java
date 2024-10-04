/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Bật bảo mật cho phương thức
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Bộ lọc JWT

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Cho phép CORS
                .and()
                .csrf().disable() // Tắt CSRF nếu không sử dụng cho API
                .authorizeRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Cho phép tất cả yêu cầu OPTIONS
                .requestMatchers("/authenticate").permitAll() // Cho phép yêu cầu đến endpoint /authenticate
                .anyRequest().authenticated(); // Yêu cầu xác thực cho các yêu cầu còn lại
        // Thêm bộ lọc JWT

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class
        );
        return http.build(); // Trả về SecurityFilterChain
    }

    @Bean
    public AuthenticationManager
            authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class
                );
        // Định nghĩa cách xác thực (thêm dịch vụ người dùng của bạn ở đây)
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
    }
}
