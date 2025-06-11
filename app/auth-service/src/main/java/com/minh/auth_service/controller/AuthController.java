package com.minh.auth_service.controller;

import com.minh.auth_service.DTOs.ChangePasswordDTO;
import com.minh.auth_service.DTOs.CreateAccountDTO;
import com.minh.auth_service.DTOs.LoginDTO;
import com.minh.auth_service.response.ResponseData;
import com.minh.auth_service.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  /// Hàm thực hiện đăng ký tài khoản mới.
  /// DONE.
  @PostMapping("/register")
  public ResponseEntity<ResponseData> register(@RequestBody @Valid CreateAccountDTO createAccountDTO) {
    ResponseData response = authService.register(createAccountDTO);
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
  public ResponseEntity<ResponseData> logout(@RequestParam String token) {
    ResponseData response = authService.logout(token);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện inactive tài khoản.
  @PostMapping(value = "/{accountId}/inactive")
  public ResponseEntity<ResponseData> inactiveAccount(@PathVariable String accountId) {
    ResponseData response = authService.inactiveAccount(accountId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thưc hiện active tài khoản.
  @PostMapping(value = "/{accountId}/active")
  public ResponseEntity<ResponseData> activeAccount(@PathVariable String accountId) {
    ResponseData response = authService.activeAccount(accountId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /// Hàm thực hiện đổi mật khẩu.
  @PostMapping(value = "/change-password")
  public ResponseEntity<ResponseData> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
    if (!StringUtils.hasText("ACCOUNT-ID")) {
      return ResponseEntity.status(403).body(new ResponseData(403, "ACCOUNT-ID is required"));
    }
    String accountId = request.getHeader("ACCOUNT-ID");
    log.info("Received request to change password for accountId: {}", accountId);

    ResponseData response = authService.changePassword(accountId, changePasswordDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}