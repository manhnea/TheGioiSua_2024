/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Hieu
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Tên đăng nhập chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9), không được chứa khoảng trắng")
    @Size(min = 6, max = 50, message = "Tên đăng nhập phải từ 6 đến 50 ký tự")
    private String username;


    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

//    @NotBlank(message = "Họ và tên không được để trống")
//    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ ]+$", message = "Họ và tên chỉ được chứa chữ cái và dấu cách")
//    @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự")
    private String fullname;

    private Date registrationdate;

//    @NotBlank(message = "Số điện thoại không được để trống")
//    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    private String phonenumber;

//    @NotBlank(message = "Địa chỉ không được để trống")
//    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ0-9,./\\s]+$", message = "Địa chỉ chỉ được chứa chữ cái, số, dấu phẩy, chấm, gạch chéo, và khoảng trắng")
//    @Size(max = 150, message = "Địa chỉ không được vượt quá 150 ký tự")
    private String address;


    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;



    private int status;
    
    @ManyToOne
    @JoinColumn(name = "roleid")
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    
    
    
}
