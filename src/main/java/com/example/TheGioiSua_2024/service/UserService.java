/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.repository.RoleRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IUserService;
import com.example.TheGioiSua_2024.util.EmailValidator;
import jakarta.transaction.Transactional;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;

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
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Tên Người Dùng Đã Tồn Tại!")); // Mã trạng thái 409
        } else if (iUserRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Email đã được sử dụng!")); // Mã trạng thái 409
        } else if (!EmailValidator.isValidEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Email không đúng định dạng!")); // Mã trạng thái 409
        } else {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFullname(registerDto.getFullname());
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setRegistrationdate(new Date(System.currentTimeMillis()));
            Role role = iRoleRepository.findById(2L).orElseThrow(); // 2L user
            user.setRole(role);
            iUserRepository.save(user);
            return ResponseEntity.ok(Collections.singletonMap("message", "Tạo Tài Khoản Thành Công")); // Mã trạng thái 200
        }
    }

    @Override
    public ResponseEntity<?> authenticate(LoginDto loginDto) {
        try {
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            // Thiết lập ngữ cảnh bảo mật
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tìm người dùng trong cơ sở dữ liệu
            User user = iUserRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));

            // Tạo token
            String token = jwtUtilities.generateToken(user.getId(), user.getUsername(), user.getRole().getRoleName());

            // Trả về token nếu xác thực thành công
            return ResponseEntity.ok(Collections.singletonMap("token", token));

        } catch (AuthenticationException e) {
            // Nếu xác thực thất bại do thông tin không hợp lệ
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Đăng nhập không thành công"));
        } catch (ResponseStatusException e) {
            // Nếu người dùng không tồn tại
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        }
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = iUserRepository.findById(id).orElseThrow();
        if (user == null) {
            System.out.println("null");
        }
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getFullname(), user.getRegistrationdate(), user.getPhonenumber(), user.getAddress(), user.getEmail(), user.getRole().getRoleName());
        return userDto;
    }

}