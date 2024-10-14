package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usagecapacity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usagecapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    @NotBlank(message = " Đơn vị không được để trống")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Đơn vị chỉ được chứa các ký tự chữ và số (A-Z, a-z)")
    private String unit;
    private int status;
}
