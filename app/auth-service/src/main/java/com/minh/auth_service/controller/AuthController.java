package com.minh.auth_service.controller;

import ch.qos.logback.core.util.StringUtil;
import com.minh.auth_service.DTOs.AccountDTO;
import com.minh.auth_service.DTOs.LoginDTO;
import com.minh.auth_service.DTOs.RequestPasswordDTO;
import com.minh.auth_service.response.ResponseData;
import com.minh.auth_service.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  /// Hàm thực hiện đăng ký tài khoản mới.
  /// DONE.
  @PostMapping("/register")
  public ResponseEntity<ResponseData> register(@RequestBody @Valid AccountDTO accountDTO) {
    ResponseData response = authService.register(accountDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện xác thực tài khoản.
  /// DONE.
  @GetMapping("/verify-email")
  public ResponseEntity<ResponseData> verifyEmail(@RequestParam(required = true) String token) {
    ResponseData response = authService.verifyEmail(token);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện đăng nhập tài khoản.
  /// DONE.
  @PostMapping("/login")
  public ResponseEntity<ResponseData> login(@RequestBody @Valid LoginDTO loginDTO) {
    ResponseData response = authService.login(loginDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện lấy thông tin tài khoản đã đăng nhập.
  @GetMapping("/profile")
  public ResponseEntity<ResponseData> getProfile(HttpServletRequest request) {
    if (!StringUtils.hasText(request.getHeader("account_id"))) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResponseData(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null));
    }
    String accountId = request.getHeader("account_id");
    ResponseData response = authService.getProfile(accountId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện lấy lại mật khẩu.
  /// DONE
  @GetMapping(value = "/forgot-password")
  public ResponseEntity<ResponseData> forgotPassword(@RequestParam String email, @RequestParam String host) {
    ResponseData response = authService.forgotPassword(email, host);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện reset password.
  /// DONE
  @PostMapping(value = "/reset-password")
  public ResponseEntity<ResponseData> resetPassword(@RequestParam String token, @RequestBody @Valid RequestPasswordDTO requestPasswordDTO) {
    ResponseData response = authService.resetPassword(token, requestPasswordDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Hàm thực hiện refresh token.
  @GetMapping(value = "/refresh-token")
  public ResponseEntity<ResponseData> refreshToken(@RequestParam String token) {
    ResponseData response = authService.refreshToken(token);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Hàm thực hiện đăng xuất tài khoản.
  @PostMapping(value = "/logout")
  public ResponseEntity<ResponseData> logout(@RequestParam String token ) {
    ResponseData response = authService.logout(token);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}