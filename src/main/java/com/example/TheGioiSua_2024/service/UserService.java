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
import com.example.TheGioiSua_2024.util.Status;
import com.example.TheGioiSua_2024.util.UserValidator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
    private final JavaMailSender mailSender;
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
        // Validate username
        if (registerDto.getUsername() == null || registerDto.getUsername().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Tên người dùng không được để trống!"));
        } else if (!UserValidator.isValidUsername(registerDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Tên người dùng không hợp lệ hoặc chứa dấu cách!"));
        } else if (iUserRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Tên Người Dùng Đã Tồn Tại!")); // Mã trạng thái 409
        }

        // Validate password
        if (registerDto.getPassword() == null || registerDto.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Mật khẩu không được để trống!"));
        } else if (registerDto.getPassword().length() < 6) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Mật khẩu phải có ít nhất 6 ký tự!"));
        } else if (registerDto.getPassword().contains(" ")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Mật khẩu không được chứa dấu cách!"));
        }

        // Validate fullname
        if (registerDto.getFullname() == null || registerDto.getFullname().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Họ tên không được để trống!"));
        } else if (!UserValidator.isValidFullName(registerDto.getFullname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Họ tên không đúng định dạng!"));
        }

        // Validate email
        if (registerDto.getEmail() == null || registerDto.getEmail().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Email không được để trống!"));
        } else if (!UserValidator.isValidEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Email không đúng định dạng hoặc chứa dấu cách!")); // Mã trạng thái 409
        } else if (iUserRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "Email đã được sử dụng!")); // Mã trạng thái 409
        }

        // Save new user if all validations pass
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFullname(registerDto.getFullname());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRegistrationdate(new Date(System.currentTimeMillis()));
        user.setStatus(Status.Inactive); // Đặt trạng thái chưa xác minh
        Role role = iRoleRepository.findById(2L).orElseThrow(); // 2L user role
        user.setRole(role);
        iUserRepository.save(user);

        // Tạo token xác minh và gửi email
        String verificationToken = jwtUtilities.generateVerificationToken(user.getId());
        user.setVerificationToken(verificationToken);
        user.setTokenCreationTime(new Timestamp(System.currentTimeMillis()));
        iUserRepository.save(user);

        sendVerificationEmail(user.getEmail(), verificationToken);

        return ResponseEntity.ok(Collections.singletonMap("message", "Tạo Tài Khoản Thành Công. Vui lòng kiểm tra email để xác minh tài khoản."));
    }

    private void sendVerificationEmail(String email, String token) {
        String subject = "Xác minh tài khoản của bạn";
        String verificationUrl = "http://localhost:3000/login/" + token; 
        String message = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\n" +
                "    <!-- Google Font css -->\n" +
                "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com/\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css2?family=Public+Sans:wght@100;200;300;400;500;600;700;800;900&display=swap\">\n" +
                "    <link href=\"https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200;300;400;600;700;800;900&display=swap\" rel=\"stylesheet\">\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        body {\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "            width: 650px;\n" +
                "            font-family: 'Public Sans', sans-serif;\n" +
                "            background-color: #e2e2e2;\n" +
                "            display: block;\n" +
                "        }\n" +
                "\n" +
                "        .mb-3 {\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        ul {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        li {\n" +
                "            display: inline-block;\n" +
                "            text-decoration: unset;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        h5 {\n" +
                "            margin: 10px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "\n" +
                "        .text-center {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .header-menu ul li+li {\n" +
                "            margin-left: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .header-menu ul li a {\n" +
                "            font-size: 14px;\n" +
                "            color: #252525;\n" +
                "            font-weight: 500;\n" +
                "        }\n" +
                "\n" +
                "        .password-button {\n" +
                "            background-color: #0DA487;\n" +
                "            border: none;\n" +
                "            color: #fff;\n" +
                "            padding: 14px 26px;\n" +
                "            font-size: 18px;\n" +
                "            border-radius: 6px;\n" +
                "            font-weight: 700;\n" +
                "            font-family: 'Nunito Sans', sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        .footer-table {\n" +
                "            position: relative;\n" +
                "        }\n" +
                "\n" +
                "        .footer-table::before {\n" +
                "            position: absolute;\n" +
                "            content: \"\";\n" +
                "            background-image: url(images/footer-left.svg);\n" +
                "            background-position: top right;\n" +
                "            top: 0;\n" +
                "            left: -71%;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            background-repeat: no-repeat;\n" +
                "            z-index: -1;\n" +
                "            background-size: contain;\n" +
                "            opacity: 0.3;\n" +
                "        }\n" +
                "\n" +
                "        .footer-table::after {\n" +
                "            position: absolute;\n" +
                "            content: \"\";\n" +
                "            background-image: url(images/footer-right.svg);\n" +
                "            background-position: top right;\n" +
                "            top: 0;\n" +
                "            right: 0;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            background-repeat: no-repeat;\n" +
                "            z-index: -1;\n" +
                "            background-size: contain;\n" +
                "            opacity: 0.3;\n" +
                "        }\n" +
                "\n" +
                "        .theme-color {\n" +
                "            color: #0DA487;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"margin: 20px auto;\">\n" +
                "    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: white; width: 100%; box-shadow: 0px 0px 14px -4px rgba(0, 0, 0, 0.27);\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <table class=\"content-table\" style=\"margin-bottom: -6px;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                        <thead>\n" +
                "                            <tr>\n" +
                "                                <td>\n" +
                "                                    <img src=\"https://i.ibb.co/6ZR8nsm/welcome-poster.jpg\" alt=\"Welcome Poster\" style=\"display: block; margin: 0 auto;\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </thead>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table class=\"content-table\" style=\"margin-top: 40px;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                        <thead>\n" +
                "                            <tr style=\"display: block;\">\n" +
                "                                <td style=\"display: block;\">\n" +
                "                                    <h3 style=\"font-weight: 700; font-size: 20px; margin: 0; text-transform: uppercase;text-align: center;\">Xin chào bạn và Chào mừng đến với SuaFpoly!</h3>\n" +
                "                                </td>\n" +
                "\n" +
                "                                <td>\n" +
                "                                    <p style=\"font-size: 14px; font-weight: 600; width: 82%; margin: 8px auto 0; line-height: 1.5; color: #939393; font-family: 'Nunito Sans', sans-serif;\">\n" +
                "                                        Chúng tôi hy vọng sản phẩm của chúng tôi sẽ dẫn bạn, như nhiều người trước bạn, đến một nơi mà ý tưởng của bạn có thể nảy sinh và phát triển. Trước khi bắt đầu, chúng tôi cần bạn xác minh email của mình.\n" +
                "                                    </p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </thead>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table class=\"button-table\" style=\"margin: 34px 0;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                        <thead>\n" +
                "                            <tr style=\"display: block;\">\n" +
                "                                <td style=\"display: block; text-align: center;\">\n" +
                "                    <a href=\"" + verificationUrl + "\" class=\"password-button\">Xác minh Email</a>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </thead>\n" +
                "                    </table>\n" +
                "\n" +
                "                    <table class=\"content-table\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                        <thead>\n" +
                "                            <tr style=\"display: block;\">\n" +
                "                                <td style=\"display: block;\">\n" +
                "                                    <p style=\"font-size: 14px; font-weight: 600; width: 82%; margin: 0 auto; line-height: 1.5; color: #939393; font-family: 'Nunito Sans', sans-serif;\">\n" +
                "                                        Nếu bạn có bất kỳ câu hỏi nào, vui lòng gửi email cho chúng tôi tại <span class=\"theme-color\">suaFpoly</span> hoặc truy cập vào <span class=\"theme-color\">Câu hỏi thường gặp</span>. Bạn cũng có thể trò chuyện với nhân viên trong giờ làm việc để được hỗ trợ.\n" +
                "                                    </p>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </thead>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>\n";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Không thể gửi email xác minh", e);
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

            if (user.getStatus() != Status.Active) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Tài khoản chưa được xác minh."));
            }

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
    public ResponseEntity<?> verifyAccount(String token) {
        try {
            // Kiểm tra token
            Long userId = jwtUtilities.verifyVerificationToken(token);
            User user = iUserRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));

            // Kiểm tra xem token có còn tồn tại và khớp với token lưu trong database không
            if (user.getVerificationToken() == null || !user.getVerificationToken().equals(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", "Token không hợp lệ hoặc đã hết hạn."));
            }

            // Kiểm tra xem token có còn hiệu lực 20 phút không
            long elapsedTime = System.currentTimeMillis() - user.getTokenCreationTime().getTime();
            if (elapsedTime > 20 * 60 * 1000) { // 20 phút
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Token xác minh đã hết hạn."));
            }

            // Xác minh tài khoản
            user.setStatus(Status.Active);
            user.setVerificationToken(null); // Xóa token sau khi xác minh để không dùng lại được
            iUserRepository.save(user);

            return ResponseEntity.ok(Collections.singletonMap("message", "Tài khoản đã được xác minh thành công."));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("error", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Token xác minh không hợp lệ hoặc đã hết hạn."));
        }
    }
    @Scheduled(fixedDelay = 60000) // Kiểm tra mỗi 1 phút
    public void deleteUnverifiedUsers() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        iUserRepository.findAll().stream()
                .filter(user -> user.getStatus() == Status.Inactive
                        && currentTimestamp.getTime() - user.getTokenCreationTime().getTime() >= 20 * 60 * 1000)
                .forEach(user -> iUserRepository.delete(user));
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
