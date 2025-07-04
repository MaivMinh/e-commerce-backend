package com.minh.auth_service.services;

import com.minh.auth_service.DTOs.*;
import com.minh.auth_service.model.Account;
import com.minh.auth_service.model.Role;
import com.minh.auth_service.model.Status;
import com.minh.auth_service.repository.AccountRepository;
import com.minh.auth_service.response.ResponseData;
import com.minh.grpc_service.auth.AuthInfo;
import com.minh.grpc_service.auth.AuthRequest;
import com.minh.grpc_service.auth.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
  private final JwtUtilsService jwtUtilsService;
  private final HttpServletResponse httpServletResponse;
  @Value("${spring.application.security.jwt.access-token-key}")
  private String accessToken;
  @Value("${spring.application.security.jwt.refresh-token-key}")
  private String refreshToken;
  @Value("${spring.application.security.jwt.access-token-expiration:43200000}")
  private long expiration;
  @Value("${spring.application.security.jwt.refresh-token-expiration:1296000000}")
  private long refreshTokenExpiration;
  @Value("${spring.application.server.host}")
  private String host;

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final RedisService redisService;
  private final StreamBridge streamBridge;

  private void createUserFunction(Account account) {
    /// Trigger event to create a new user in user-service using Spring cloud stream Kafka.
    CreatedAccountMessageDTO createdAccountMessageDTO = CreatedAccountMessageDTO.builder()
            .accountId(account.getId())
            .username(account.getUsername())
            .email(account.getEmail())
            .fullName(account.getName())
            .build();

    /// Sử dụng StreamBridge để gửi message đến user-service.
    var result = streamBridge.send("createUserFunction-out-0", createdAccountMessageDTO);
    if (result) {
      log.info("Message sent to create user in user-service successfully");
    } else {
      log.error("Failed to create user in user-service");
    }
  }

  private boolean inactiveUserFunction(String accountId) {
    /// Trigger event to inactive a user in user-service using Spring cloud stream Kafka.
    InactiveUserMessageDTO inactiveUserMessageDTO = InactiveUserMessageDTO.builder()
            .accountId(accountId)
            .build();

    /// Sử dụng StreamBridge để gửi message đến user-service.
    var result = streamBridge.send("inactiveUserFunction-out-0", inactiveUserMessageDTO);
    if (result) {
      log.info("Message sent to inactive user in user-service successfully");
    } else {
      log.error("Failed to inactive user in user-service");
    }
    return result;
  }


  private boolean activeUserFunction(String accountId) {
    ActiveUserMessageDTO activeUserMessageDTO = ActiveUserMessageDTO.builder()
            .accountId(accountId)
            .build();
    /// Trigger event to active a user in user-service using Spring cloud stream Kafka.
    var result = streamBridge.send("activeUserFunction-out-0", activeUserMessageDTO);
    if (result) {
      log.info("Message sent to active user in user-service successfully");
    } else {
      log.error("Failed to active user in user-service");
    }
    return result;
  }

  public ResponseData register(CreateAccountDTO createAccountDTO) {
    try {
      /// Hàm tạo một Account mới bên trong hệ thống.
      Optional<Account> saved = accountRepository.checkWhetherAccountIsAlreadyExistsOrNot(createAccountDTO.getUsername(), createAccountDTO.getEmail());
      if (saved.isPresent()) {
        return ResponseData.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Username or email already exists")
                .build();
      }
      Account account = Account.builder()
              .id(UUID.randomUUID().toString())
              .username(createAccountDTO.getUsername())
              .password(passwordEncoder.encode(createAccountDTO.getPassword()))
              .email(createAccountDTO.getEmail())
              .name(createAccountDTO.getName())
              .role(Role.valueOf("customer")) // Mặc định là customer
              .status(Status.active)
              .avatar(createAccountDTO.getAvatar())
              .build();

      accountRepository.save(account);

      this.createUserFunction(account);

      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Account registered successfully.")
              .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to register account", e);
    }
  }

  public ResponseData login(LoginDTO accountDTO) {
    Optional<Account> account = accountRepository.findAccountByUsername(accountDTO.getUsername());
    if (account.isEmpty()) {
      return ResponseData.builder()
              .status(HttpStatus.NOT_FOUND.value())
              .message("Account not found")
              .build();
    }

    if (!StringUtils.hasText(accountDTO.getPassword())) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Password must not be empty")
              .build();
    }

    /// Hàm thực hiện việc đăng nhập người dùng.
    Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(accountDTO.getUsername(), accountDTO.getPassword());

    Authentication authenticated = authenticationManager.authenticate(authentication);
    if (authenticated == null || !authenticated.isAuthenticated()) {
      log.error("Unauthenticated user");
      return ResponseData.builder()
              .status(HttpStatus.UNAUTHORIZED.value())
              .message("Invalid username or password")
              .build();
    }
    Account saved = account.get();
    if (saved.getStatus().equals(Status.inactive)) {
      return ResponseData.builder()
              .status(HttpStatus.FORBIDDEN.value())
              .message("Account is not activated. Please contact support to activate your account.")
              .build();
    }

    /// Tạo access token và refresh token cho người dùng.
    SecretKey secretKey = Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8));
    SecretKey refreshKey = Keys.hmacShaKeyFor(refreshToken.getBytes(StandardCharsets.UTF_8));

    String token = Jwts.builder()
            .setIssuer("auth-service")
            .setSubject("Access Token")
            .addClaims(Map.of("account_id", saved.getId(), "role", saved.getRole().toString()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration)) ///  12 hour.
            .setIssuedAt(new Date())
            .signWith(secretKey)
            .compact();

    String refreshToken = Jwts.builder()
            .setIssuer("auth-service")
            .setSubject("Refresh Token")
            .addClaims(Map.of("account_id", saved.getId()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration)) ///  15 days.
            .setIssuedAt(new Date())
            .signWith(refreshKey)
            .compact();


    /// Xóa refresh token cũ nếu có, để tránh việc sử dụng lại refresh token cũ.
    redisService.delete("refresh_token:" + saved.getId()); // Xóa refresh token cũ nếu có

    /// Trả dữ liệu về cho client, trả về access token và refresh token. Bên cạnh đó, lưu trữ refresh token vào Redis để có thể sử dụng lại sau này.
    redisService.set("refresh_token:" + saved.getId(), refreshToken, refreshTokenExpiration / 1000); // Lưu trữ refresh token vào Redis với thời gian hết hạn tương ứng

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Login successfully")
            .data(Map.of("accessToken", token, "refreshToken", refreshToken))
            .build();
  }

  public ResponseData logout(String token) {
    if (!StringUtils.hasText(accessToken)) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Access token is empty")
              .build();
    }

    Claims claims;
    try {
      claims = Jwts.parserBuilder()
              .setSigningKey(Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8)))
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (RuntimeException e) {
      return ResponseData.builder()
              .status(HttpStatus.UNAUTHORIZED.value())
              .message("Access token is invalid")
              .build();
    }

    String accountId = claims.get("account_id").toString();
    redisService.delete("refresh_token:" + accountId); // Xóa refresh token khỏi Redis
    /// Thêm access token vào blacklist (nếu có)

    // Parse token để lấy thời gian hết hạn
    Date expiration = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();

    long ttl = Duration.between(Instant.now(), expiration.toInstant()).getSeconds();
    redisService.set("blacklist:" + token, "true", ttl); // Lưu access token vào blacklist với thời gian hết hạn tương ứng
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Logout successfully")
            .build();
  }

  public AuthResponse authenticate(AuthRequest request) {
    String token = request.getToken();

    if (!StringUtils.hasText(token)) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
              .setMessage("Token is empty")
              .build();
    }

    Claims claims;
    try {
      claims = Jwts.parserBuilder()
              .setSigningKey(Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8)))
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (ExpiredJwtException e) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
              .setStatus(498)
              .setMessage("Token has expired")
              .build();
    } catch (RuntimeException e) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
              .setStatus(HttpStatus.UNAUTHORIZED.value())
              .setMessage("Token is invalid")
              .build();
    }

    String accountId = claims.get("account_id").toString();
    Optional<Account> account = accountRepository.findAccountById(accountId);
    if (account.isEmpty()) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
              .setMessage("Account not found")
              .build();
    }

    Account saved = account.get();
    if (saved.getStatus().equals(Status.inactive)) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
              .setMessage("Account is not activated. Please verify your email first.")
              .build();
    }
    String role = saved.getRole().toString();

    return AuthResponse.newBuilder()
            .setIsValid(true)
            .setMessage("Authentication successful")
            .setAuthInfo(AuthInfo.newBuilder()
                    .setAccountId(accountId)
                    .setRole(role)
                    .build())
            .build();
  }

  public ResponseData refreshToken(String token) {
    if (!StringUtils.hasText(token)) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Refresh token is empty")
              .build();
    }

    try {
      // Verify using the refresh token key, not access token key
      SecretKey refreshKey = Keys.hmacShaKeyFor(refreshToken.getBytes(StandardCharsets.UTF_8));
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(refreshKey)
              .build()
              .parseClaimsJws(token)
              .getBody();

      String accountId = claims.get("account_id").toString();
      // Check if the token exists in Redis
      String storedToken = redisService.get("refresh_token:" + accountId);
      if (storedToken == null || !storedToken.equals(token)) {
        return ResponseData.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Refresh token is invalid or expired")
                .build();
      }

      Optional<Account> account = accountRepository.findAccountById(accountId);
      if (account.isEmpty()) {
        return ResponseData.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Account not found")
                .build();
      }

      Account saved = account.get();
      if (saved.getStatus().equals(Status.inactive)) {
        return ResponseData.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Account is not activated. Please contact support to activate your account.")
                .build();
      }

      // Generate new access token
      SecretKey accessKey = Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8));
      String newAccessToken = Jwts.builder()
              .setIssuer("auth-service")
              .setSubject("Access Token")
              .addClaims(Map.of("account_id", saved.getId(), "role", saved.getRole().toString()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .setIssuedAt(new Date())
              .signWith(accessKey)
              .compact();


      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Token refreshed successfully")
              .data(Map.of("accessToken", newAccessToken))
              .build();

    } catch (ExpiredJwtException e) {
      return ResponseData.builder()
              .status(499)
              .message("Refresh token has expired")
              .build();
    } catch (Exception e) {
      log.error("Error refreshing token", e);
      return ResponseData.builder()
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message("Failed to refresh token: " + e.getMessage())
              .build();
    }
  }

  public ResponseData inactiveAccount(String accountId) {
    /// Xử lý inactive tài khoản.
    Account account = accountRepository.findAccountById(accountId).orElseThrow(
            () -> new RuntimeException("Account not found")
    );
    account.setStatus(Status.inactive);
    accountRepository.save(account);

    /// Gọi hàm để inactive user trong user-service.
    boolean result = this.inactiveUserFunction(accountId);
    if (!result) {
      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Inactive account successfully, but failed to inactive user in user-service")
              .build();
    }
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Inactive account and user successfully")
            .build();
  }

  public ResponseData activeAccount(String accountId) {
    /// Xử lý active tài khoản.
    Account account = accountRepository.findAccountById(accountId).orElseThrow(
            () -> new RuntimeException("Account not found")
    );
    account.setStatus(Status.active);
    accountRepository.save(account);

    /// Gọi hàm để inactive user trong user-service.
    boolean result = this.activeUserFunction(accountId);
    if (!result) {
      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Inactive account successfully, but failed to inactive user in user-service")
              .build();
    }
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Inactive account and user successfully")
            .build();

  }

  public ResponseData changePassword(ChangePasswordDTO changePasswordDTO) {
    String accountId = changePasswordDTO.getAccountId();
    if (!changePasswordDTO.getConfirmNewPassword().equals(changePasswordDTO.getNewPassword())) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("New password and confirm new password do not match")
              .build();
    }

    Account account = accountRepository.findAccountById(accountId).orElseThrow(
            () -> new RuntimeException("Account not found")
    );

    if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), account.getPassword())) {
      return ResponseData.builder()
              .status(HttpStatus.UNAUTHORIZED.value())
              .message("Old password is incorrect")
              .build();
    }

    account.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
    accountRepository.save(account);
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Password changed successfully")
            .build();
  }
}