/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.BearerToken;
import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.repository.RoleRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IUserService;
import jakarta.transaction.Transactional;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository iUserRepository;
    private final RoleRepository iRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @Override
    public Role saveRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public User saverUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if (iUserRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("User is already taken !", HttpStatus.SEE_OTHER);
        }
        else if(iUserRepository.existsByEmail(registerDto.getEmail()))  {
            return new ResponseEntity<>("Email is already taken !", HttpStatus.SEE_OTHER);
        } else {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFullname(registerDto.getFullname());
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            //By Default , he/she is a simple user
            user.setRegistrationdate(new Date(System.currentTimeMillis()));
            Role role = iRoleRepository.findById(2l).orElseThrow();//2l user
            user.setRole(role);
            iUserRepository.save(user);
            String token = jwtUtilities.generateToken(null, registerDto.getUsername(), role.getRoleName());
//            return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);
            return new ResponseEntity<>("Tạo Tài Khoản Thành Công", HttpStatus.OK);

        }
    }

    @Override
    public String authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
//        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));

        String token = jwtUtilities.generateToken(user.getId(), user.getUsername(), user.getRole().getRoleName());
        return token;
    }
    
    @Override
    public UserDto findUserById(Long id) {
        User user = iUserRepository.findById(id).orElseThrow();
        if(user == null){
            System.out.println("null");
        }
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getFullname(), user.getRegistrationdate(), user.getPhonenumber(), user.getAddress(), user.getEmail(), user.getRole().getRoleName());
        return userDto;
    }
    

}
