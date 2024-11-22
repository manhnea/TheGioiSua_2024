package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.ForgotPasswordDto;
import com.example.TheGioiSua_2024.dto.LoginDto;
import com.example.TheGioiSua_2024.dto.RegisterDto;
import com.example.TheGioiSua_2024.dto.UserDto;
import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.repository.RoleRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IUserService;
import com.example.TheGioiSua_2024.util.EmailSend;
import com.example.TheGioiSua_2024.util.Status;
import com.example.TheGioiSua_2024.util.TelegramNotifier;
import com.example.TheGioiSua_2024.util.UserValidator;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
  private final JavaMailSender mailSender;
  private final EmailSend emailSend;
  private final MessageSource messageSource;

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
          .body(
              Collections.singletonMap("error", "Tên người dùng không hợp lệ hoặc chứa dấu cách!"));
    } else if (iUserRepository.existsByUsername(registerDto.getUsername())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Collections.singletonMap("error", "Tên Người Dùng Đã Tồn Tại!"));
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
          .body(
              Collections.singletonMap("error", "Email không đúng định dạng hoặc chứa dấu cách!"));
    } else if (iUserRepository.existsByEmail(registerDto.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Collections.singletonMap("error", "Email đã được sử dụng!"));
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

    // Sử dụng  để gửi email xác minh
    emailSend.sendVerificationEmail(user.getEmail(), verificationToken);

    return ResponseEntity.ok(Collections.singletonMap("message",
        "Tạo Tài Khoản Thành Công. Vui lòng kiểm tra email để xác minh tài khoản."));
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
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));

      if (user.getStatus() != Status.Active) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Collections.singletonMap("error", "Tài khoản chưa được xác minh."));
      }

      // Tạo token
      String token = jwtUtilities.generateToken(user.getId(), user.getUsername(),
          user.getRole().getRoleName());

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
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));

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

      // Gửi thông báo đến Telegram và Zalo sau khi xác minh thành công
      String message = "Tài khoản của " + user.getUsername() + " đã được xác minh thành công.";
      TelegramNotifier telegramNotifier = new TelegramNotifier();
      telegramNotifier.sendUserDeletionNotification(message);
      telegramNotifier.sendMessageZalo(message);

      return ResponseEntity.ok(
          Collections.singletonMap("message", "Tài khoản đã được xác minh thành công."));
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode())
          .body(Collections.singletonMap("error", e.getReason()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "Token xác minh không hợp lệ hoặc đã hết hạn."));
    }
  }

  @Override
  public UserDto findUserById(Long id) {
    User user = iUserRepository.findById(id).orElseThrow();
    if (user == null) {
      System.out.println("null");
    }
    UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getFullname(),
        user.getRegistrationdate(), user.getPhonenumber(), user.getAddress(), user.getEmail(),
        user.getRole().getRoleName());
    return userDto;
  }

  @Override
  public ResponseEntity<?> forgotPassword(ForgotPasswordDto forgotPasswordDto) {
    String email = forgotPasswordDto.getEmail();

    // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
    User user = iUserRepository.findByEmail(email)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email không tồn tại"));

    // Tạo token khôi phục mật khẩu
    String recoveryToken = jwtUtilities.generateRecoveryToken(user.getId());
    user.setVerificationToken(recoveryToken);
    user.setTokenCreationTime(new Timestamp(System.currentTimeMillis()));
    iUserRepository.save(user);

    // Gửi email khôi phục mật khẩu
    emailSend.sendRecoveryEmail(user.getEmail(), recoveryToken);

    return ResponseEntity.ok(Collections.singletonMap("message",
        "Vui lòng kiểm tra email của bạn để khôi phục mật khẩu."));
  }

  @Override
  public ResponseEntity<?> resetPassword(String token, String newPassword) {
    // Validate the recovery token and retrieve userId
    Long userId = jwtUtilities.verifyRecoveryToken(token);

    // Find the user by ID
    User user = iUserRepository.findById(userId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));

    // Check if the verification token matches the provided token
    if (user.getVerificationToken() == null || !user.getVerificationToken().equals(token)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "Token không hợp lệ hoặc đã hết hạn."));
    }

    // Update the password
    user.setPassword(passwordEncoder.encode(newPassword));

    // Clear the verification token to mark it as used
    user.setVerificationToken(null); // Xóa token khôi phục mật khẩu
    iUserRepository.save(user); // Save user changes

    return ResponseEntity.ok(
        Collections.singletonMap("message", "Mật khẩu đã được đặt lại thành công."));
  }

  @Override
  public ResponseEntity<?> changePassword(Long userId, String oldPassword, String newPassword) {
    // Retrieve user by ID
    User user = iUserRepository.findById(userId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));

    // Check if old password matches the stored password
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Mật khẩu cũ không chính xác."));
    }

    // Validate the new password
    if (newPassword == null || newPassword.trim().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "Mật khẩu mới không được để trống!"));
    } else if (newPassword.length() < 6) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "Mật khẩu mới phải có ít nhất 6 ký tự!"));
    } else if (newPassword.contains(" ")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "Mật khẩu mới không được chứa dấu cách!"));
    }

    // Update the password
    user.setPassword(passwordEncoder.encode(newPassword));
    iUserRepository.save(user);

    return ResponseEntity.ok(
        Collections.singletonMap("message", "Mật khẩu đã được thay đổi thành công."));
  }

  @Override
  public long countUsersByRoleAndStatus() {
    return iUserRepository.countUsersByStatusAndRole();
  }

  @Override
  public List<String> findTop5() {
    return iUserRepository.findAllUsernames();
  }

  @Override
  public List<User> getAllUsers() {
    return iUserRepository.findAll();
  }

  @Override
  public ResponseEntity<?> updatePhoneNumber(User user) {
    try {
      // Tìm người dùng theo ID
      User u = iUserRepository.findById(user.getId())
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
      u.setPhonenumber(user.getPhonenumber());
      iUserRepository.save(u);
      return ResponseEntity.ok("User updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while updating the user");
    }
  }

  @Override
  public ResponseEntity<?> updateAddress(User user) {
    try {
      // Tìm người dùng theo ID
      User u = iUserRepository.findById(user.getId())
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Người dùng không tồn tại"));
      u.setAddress(user.getAddress());
      iUserRepository.save(u);
      return ResponseEntity.ok("User updated successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An error occurred while updating the user");
    }
  }

}
