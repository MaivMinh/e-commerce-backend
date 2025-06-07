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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
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

  /// Hàm thực hiện đăng nhập tài khoản.
  /// DONE.
  @PostMapping("/login")
  public ResponseEntity<ResponseData> login(@RequestBody @Valid LoginDTO loginDTO) {
    ResponseData response = authService.login(loginDTO);
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