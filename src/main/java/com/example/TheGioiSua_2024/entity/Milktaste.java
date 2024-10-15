package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "milktaste")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Milktaste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên hương vị sữa không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Tên hương vị sữa chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9)")
    private String milktastename;
    private int status;
}
