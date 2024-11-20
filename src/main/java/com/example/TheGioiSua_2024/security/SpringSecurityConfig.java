/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SpringSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomerUserDetailsService customerUserDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors() // Cấu hình CORS
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()

        // Các endpoint không yêu cầu xác thực
        .requestMatchers(
            "/user/register",
            "/user/reset-password",
            "/user/forgot-password",
            "/user/authenticate",
            "/user/verify",
            "/user/{id}",
            "/user/updateAddress",
            "/user/updatePhoneNumber",
            "/Product/page/**",
            "/Product/page",
            "/Product/lst",
            "/Packagingunit/lst",
            "/Usagecapacity/lst",
            "/Milkdetail/page",
            "/Milkdetail/getMilkDetail",
            "/Milktype/lst",
            "/Milkbrand/lst",
            "/Targetuser/lst",
            "/Milktaste/lst",
            "/api-docs/**",
            "/swagger-ui/**"
        ).permitAll()

        // Quyền của Customer
        .requestMatchers(
            "/Invoice/getInvoices/**",
            "/payment/transactionHistory",
            "/Invoice/add",
            "/Invoicedetail/add",
            "/Userinvoice/add",
            "/Voucher/voucercode",
            "/Invoicedetail/getInvoiceDetailByUser/**",
            "/Checkout/**"
        ).hasAuthority("Customer")

        // Quyền của Staff
        .requestMatchers(
            "/user/**",
            "/Milktype/**",
            "/Milktaste/**",
            "/Packagingunit/**",
            "/Targetuser/**",
            "/Product/**",
            "/Milkdetail/**",
            "/Milkbrand/**",
            "/Invoicedetail/**",
            "/Invoice/**",
            "/Userinvoice/**",
            "/Usagecapacity/**",
            "/Voucher/lst",
            "/payment/**"
        ).hasAuthority("Staff")

        // Quyền của Admin
        .requestMatchers(
            "/user/**",
            "/admin/**",
            "/Voucher/**",
            "/Milktype/**",
            "/Milktaste/**",
            "/Packagingunit/**",
            "/Targetuser/**",
            "/Product/**",
            "/Milkdetail/**",
            "/Milkbrand/**",
            "/Invoicedetail/**",
            "/Invoice/**",
            "/Userinvoice/**",
            "/Usagecapacity/**",
            "/payment/**"
        ).hasAuthority("Admin")

        .anyRequest().authenticated(); // Tất cả yêu cầu khác phải xác thực

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // Allows all domains to access the API
    configuration.addAllowedOriginPattern(
        "*");  // Use this to allow all domains instead of addAllowedOrigin("*")

    configuration.addAllowedMethod("*");  // Allows all HTTP methods (GET, POST, PUT, DELETE, etc.)
    configuration.addAllowedHeader("*");  // Allows all headers
    configuration.setAllowCredentials(
        true);  // Allows credentials such as cookies or HTTP authentication

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
//        aaaa
  }

}
