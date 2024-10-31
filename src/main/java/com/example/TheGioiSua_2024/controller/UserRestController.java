/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.service.UserService;
import com.example.TheGioiSua_2024.service.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService iUserService;
    private final UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token) {
        ResponseEntity<?> response = userService.verifyAccount(token);

        // Template HTML với CSS và JavaScript cho các thông báo
        String htmlTemplate = "<html><head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f4f4f9; }" +
                ".message-box { max-width: 400px; padding: 20px; text-align: center; border-radius: 10px; background-color: #fff; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); }" +
                ".message-box h2 { font-size: 24px; margin-bottom: 10px; color: #4CAF50; }" +
                ".message-box p { font-size: 18px; margin-bottom: 20px; color: #333; }" +
                ".message-box .error { color: #f44336; }" +
                ".message-box .redirecting { font-size: 14px; color: #888; margin-top: 10px; }" +
                "</style>" +
                "</head><body>";

        String redirectScript = "<script>" +
                "let countdown = 3;" +
                "const countdownElem = document.getElementById('countdown');" +
                "const interval = setInterval(() => {" +
                "    countdownElem.innerText = countdown;" +
                "    countdown--;" +
                "    if (countdown < 0) {" +
                "        clearInterval(interval);" +
                "        window.location.href = '%s';" +
                "    }" +
                "}, 1000);" +
                "</script>";

        if (response.getStatusCode() == HttpStatus.OK) {
            String htmlContent = htmlTemplate +
                    "<div class='message-box'>" +
                    "<h2>Tài khoản xác minh thành công!</h2>" +
                    "<p>Đang chuyển hướng đến trang đăng nhập trong <span id='countdown'>3</span> giây...</p>" +
                    "</div>" +
                    String.format(redirectScript, "http://160.30.21.47:3000/login") +
                    "</body></html>";
            return ResponseEntity.ok().body(htmlContent);

        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String htmlContent = htmlTemplate +
                    "<div class='message-box'>" +
                    "<h2 class='error'>Token không hợp lệ hoặc đã hết hạn!</h2>" +
                    "<p>Đang chuyển hướng đến trang chủ trong <span id='countdown'>3</span> giây...</p>" +
                    "</div>" +
                    String.format(redirectScript, "http://160.30.21.47:3000") +
                    "</body></html>";
            return ResponseEntity.ok().body(htmlContent);

        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            String htmlContent = htmlTemplate +
                    "<div class='message-box'>" +
                    "<h2 class='error'>Không tìm thấy tài khoản</h2>" +
                    "<p>Đang chuyển hướng đến trang đăng ký trong <span id='countdown'>3</span> giây...</p>" +
                    "</div>" +
                    String.format(redirectScript, "http://160.30.21.47:3000/sign-up") +
                    "</body></html>";
            return ResponseEntity.ok().body(htmlContent);
        }

        // Trả về phản hồi gốc nếu không có điều kiện nào phù hợp
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody().toString());
    }


    //RessourceEndPoint:http://localhost:1234/api/user/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        return iUserService.register(registerDto);
    }

    //RessourceEndPoint:http://localhost:1234/api/user/authenticate
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) {
        return iUserService.authenticate(loginDto);
    }

    //http://localhost:1234/api/user/id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDto userDto = iUserService.findUserById(id);
        return ResponseEntity.ok(userDto);
    }

}