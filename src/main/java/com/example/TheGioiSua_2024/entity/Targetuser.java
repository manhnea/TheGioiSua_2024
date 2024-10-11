package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Tên không được để trống")
    @JsonProperty("targetuser")
    private String targetName;
    private String description;
    private int status;
}
