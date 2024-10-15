package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "targetuser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Targetuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên đối tượng không được để trống")
    @JsonProperty("targetuser")
    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ0-9 ]+$", message = "Tên đối tượng chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9)")
    private String targetName;
    private String description;
    private int status;
}
