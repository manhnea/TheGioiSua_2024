/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {
    //it's a Data Trasfer Object for registration
    @NotBlank(message = "Username is required")
    String username ;

    @NotBlank(message = "Password is required")
    String password ;

    @NotBlank(message = "Fullname is required")
    String fullname ;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email;
}
