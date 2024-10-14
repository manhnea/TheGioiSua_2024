package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "milkbrand")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Milkbrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Tên thương hiệu sữa chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9)")
    @NotBlank(message = "Tên thương hiệu sữa không được để trống")
    @Size(max = 100, message = "Tên thương hiệu sữa không được dài quá 100 ký tự")
    private String milkbrandname;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 255, message = "Mô tả không được dài quá 255 ký tự")
    private String description;

    private int status;
}
