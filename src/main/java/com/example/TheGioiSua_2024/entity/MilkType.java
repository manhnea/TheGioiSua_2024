package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "milktype")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MilkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên loại sữa không được để trống")
    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ ]+$", message = "Tên loại sữa chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9)")
    private String milkTypename;
    private String description;
    private int status;
}
