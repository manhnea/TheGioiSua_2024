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

    private String username;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserOnlineDto(String username, String role) {
        this.username = username;
        this.role = role;
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
        return username.equals(that.username) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }
}
