package com.minh.auth_service.controller;

import com.minh.auth_service.DTOs.AccountAddressDTO;
import com.minh.auth_service.DTOs.AccountDTO;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final AuthService authService;

  /// Hàm thực hiện lấy thông tin tài khoản đã đăng nhập.
  @GetMapping("/profile")
  public ResponseEntity<ResponseData> getProfile(HttpServletRequest request) {
    if (!StringUtils.hasText(request.getHeader("ACCOUNT-ID"))) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResponseData(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null));
    }
    String accountId = request.getHeader("ACCOUNT-ID");
    ResponseData response = authService.getProfile(accountId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /// Hàm thực hiện tạo mới địa chỉ.
  @PostMapping(value = "/addresses")
  public ResponseEntity<ResponseData> createAddress(@RequestBody @Valid AccountAddressDTO accountAddressDTO) {
    ResponseData response = authService.createAddress(accountAddressDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
  }
}