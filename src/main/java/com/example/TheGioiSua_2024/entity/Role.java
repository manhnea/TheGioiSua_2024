/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.entity;

import com.example.TheGioiSua_2024.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Hieu
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên quyền không được để trống")
    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ ]+$", message = "Tên quyền chỉ được chứa các ký tự chữ và số (A-Z, a-z)")
    private String roleName;
    private int status;
}
