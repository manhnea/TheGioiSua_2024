package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "packagingunit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Packagingunit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Đơn vị bao bì không được để trống")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Đơn vị bao bì chỉ được chứa các ký tự chữ và số (A-Z, a-z)")
    private String packagingunitname;
    private int status;
}
