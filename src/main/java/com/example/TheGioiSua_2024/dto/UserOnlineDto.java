/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.dto;

import java.util.Objects;

/**
 * @author Administrator
 */
public class UserOnlineDto {

  private String userId;
  private String role;
  // Getters and setters

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    UserOnlineDto that = (UserOnlineDto) obj;
    return userId.equals(that.userId) && role.equals(that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, role);
  }
}
