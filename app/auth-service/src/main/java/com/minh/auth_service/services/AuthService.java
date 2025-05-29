package com.minh.auth_service.services;

import com.minh.auth_service.DTOs.AccountDTO;
import com.minh.auth_service.DTOs.LoginDTO;
import com.minh.auth_service.DTOs.ProfileDTO;
import com.minh.auth_service.DTOs.RequestPasswordDTO;
import com.minh.auth_service.model.Account;
import com.minh.auth_service.model.Role;
import com.minh.auth_service.model.Status;
import com.minh.auth_service.repository.AccountRepository;
import com.minh.auth_service.response.ResponseData;
import com.minh.grpc_service.auth.AuthInfo;
import com.minh.grpc_service.auth.AuthRequest;
import com.minh.grpc_service.auth.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

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
  private final RestClient restClient;
  private final AuthenticationManager authenticationManager;
  private final RedisService redisService;

  private boolean sendEmail(String email, String template, int times) {
    ResponseEntity<?> response = restClient.post()
            .uri("/v3/smtp/email")
            .body(Map.of("sender", Map.of("name", "E-commerce platform", "email", "maivanminh.se@gmail.com"), "to", List.of(Map.of("email", email)), "subject", "Verify Email", "htmlContent", template))
            .retrieve()
            .toBodilessEntity();
    if (response.getStatusCode().is2xxSuccessful()) {
      return true;
    } else {
      if (times > 0) {
        log.error("Send mail failed, retrying... Remaining attempts: {}", times);
        return sendEmail(email, template, times - 1);
      } else {
        log.error("Failed to send email after multiple attempts");
        return false;
      }
    }
  }

  public ResponseData register(@Valid AccountDTO accountDTO) {
    try {
      /// Hàm tạo một Account mới bên trong hệ thống.
      Optional<Account> saved = accountRepository.checkWhetherAccountIsAlreadyExistsOrNot(accountDTO.getUsername(), accountDTO.getEmail());
      if (saved.isPresent()) {
        return ResponseData.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Username or email already exists")
                .build();
      }
      Account account = Account.builder()
              .id(UUID.randomUUID().toString())
              .username(accountDTO.getUsername())
              .email(accountDTO.getEmail())
              .role(Role.valueOf("customer")) // Mặc định là customer
              .password(passwordEncoder.encode(accountDTO.getPassword()))
              .status(Status.valueOf("inactive")) // Mặc định là inactive vì phải xác thực email trước khi kích hoạt tài khoản
              .avatar(accountDTO.getAvatar())
              .build();


      /// Thực  hiện gửi mail xác thực tới cho người dùng.
      String token = Jwts.builder()
              .setIssuer("auth-service")
              .setSubject("Verify your email")
              .addClaims(Map.of("email", account.getEmail()))
              .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 30 minutes
              .setIssuedAt(new Date())
              .signWith(Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8)))
              .compact();

      String emailTemplate = """
              <!DOCTYPE html>
              <html>
              <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <style>
                      body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
                      .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; }
                      .header { background-color: #0d253f; padding: 20px; text-align: center; }
                      .header h1 { color: #ffffff; margin: 0; }
                      .content { padding: 20px; color: #333333; }
                      .button { display: inline-block; background-color: #01b4e4; color: #ffffff; text-decoration: none; padding: 12px 24px; border-radius: 4px; margin: 20px 0; }
                      .footer { padding: 20px; text-align: center; font-size: 12px; color: #999999; }
                  </style>
              </head>
              <body>
                  <div class="container">
                      <div class="header">
                          <h1>E-commerce plaform</h1>
                      </div>
                      <div class="content">
                          <h2>Verify Your Email</h2>
                          <p>Thank you for creating an account with our E-commerce platform. Please verify your email address within 10 minutes to continue.</p>
                          <div style="text-align: center;">
                              <a href="%s/api/auth/verify-email?token=%s" class="button">Verify your email</a>
                          </div>
                          <p>If you didn't create this account, please ignore this email.</p>
                      </div>
                      <div class="footer">
                          <p>&copy; 2024 E-commerce. All rights reserved.</p>
                          <p>This is an automated email, please do not reply.</p>
                      </div>
                  </div>
              </body>
              </html>
              """.formatted(host, token);
      boolean result = sendEmail(account.getEmail(), emailTemplate, 3);
      if (!result) {
        return ResponseData.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error occurred while sending verification email")
                .build();
      }
      accountRepository.save(account);
      /// Nếu gửi mail thành công thì trả về thông báo cho người dùng.
      return ResponseData.builder()
              .status(HttpStatus.OK.value())
              .message("Account registered successfully. Please check your email to verify your account.")
              .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RuntimeException("Failed to register account", e);
    }
  }

  public ResponseData verifyEmail(String token) {
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
              .message("Token is invalid")
              .build();
    }

    String email = claims.get("email").toString();
    Optional<Account> account = accountRepository.findAccountByEmail(email);
    if (account.isEmpty()) {
      return ResponseData.builder()
              .status(HttpStatus.NOT_FOUND.value())
              .message("Account not found")
              .build();
    }

    try {
      Account saved = account.get();
      saved.setStatus(Status.active);
      accountRepository.save(saved);
    } catch (RuntimeException e) {
      return ResponseData.builder()
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message("Error occurred while updating account status")
              .build();
    }
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Email verified successfully")
            .build();
  }


  public ResponseData login(@Valid LoginDTO accountDTO) {
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
              .message("Account is not activated. Please verify your email first.")
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

    /// Trả dữ liệu về cho client, trả về access token và refresh token. Bên cạnh đó, lưu trữ refresh token vào Redis để có thể sử dụng lại sau này.
    redisService.set("refresh_token:" + saved.getId(), refreshToken, refreshTokenExpiration / 1000); // Lưu trữ refresh token vào Redis với thời gian hết hạn tương ứng

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Login successfully")
            .data(Map.of("accessToken", token, "refreshToken", refreshToken))
            .build();
  }

  /// Hàm thực hiện lấy thông tin tài khoản đã đăng nhập.
  public ResponseData getProfile(String accountId) {
    Optional<Account> account = accountRepository.findAccountById(accountId);
    if (account.isEmpty()) {
      return ResponseData.builder()
              .status(HttpStatus.NOT_FOUND.value())
              .message("Account not found")
              .build();
    }
    Account saved = account.get();
    ProfileDTO profileDTO = new ProfileDTO();
    profileDTO.setId(saved.getId());
    profileDTO.setUsername(saved.getUsername());
    profileDTO.setEmail(saved.getEmail());
    profileDTO.setRole(saved.getRole().toString());
    profileDTO.setAvatar(saved.getAvatar());

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Get profile successfully")
            .data(profileDTO)
            .build();
  }

  public ResponseData forgotPassword(String email, String host) {
    if (!StringUtils.hasText(email) || !StringUtils.hasText(host)) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Email or host is empty")
              .build();
    }
    Optional<Account> account = accountRepository.findAccountByEmail(email);

    if (account.isEmpty()) {
      return ResponseData.builder()
              .status(HttpStatus.NOT_FOUND.value())
              .message("Email not found")
              .build();
    }
    Account saved = account.get();

    String token = Jwts.builder()
            .setIssuer("auth-service")
            .setSubject("Reset Password")
            .addClaims(Map.of("email", saved.getEmail()))
            .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 30 minutes
            .setIssuedAt(new Date())
            .signWith(Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8)))
            .compact();

    /// Thực hiện gửi email về cho người dùng.

    String resetPasswordTemplate = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { font-family: 'Arial', sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; }
                    .header { background-color: #0d253f; padding: 20px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; }
                    .content { padding: 20px; color: #333333; }
                    .button { display: inline-block; background-color: #01b4e4; color: #ffffff; text-decoration: none; padding: 12px 24px; border-radius: 4px; margin: 20px 0; }
                    .footer { padding: 20px; text-align: center; font-size: 12px; color: #999999; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>E-commerce platform</h1>
                    </div>
                    <div class="content">
                        <h2>Reset Your Password</h2>
                        <p>We received a request to reset your password. Click the button below to create a new password.</p>
                        <div style="text-align: center;">
                            <a href="%s/account/reset-password?token=%s" class="button">Reset Password</a>
                        </div>
                        <p>If you didn't request this, please ignore this email.</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 E-commerce. All rights reserved.</p>
                        <p>This is an automated email, please do not reply.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(host, token);

    boolean result = sendEmail(saved.getEmail(), resetPasswordTemplate, 3);
    if (!result) {
      return ResponseData.builder()
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message("Error occurred while sending email")
              .build();
    }
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Reset password email sent successfully. Please check your email to reset your password.")
            .build();
  }

  public ResponseData resetPassword(String token, RequestPasswordDTO requestPasswordDTO) {
    /// Hàm thực hiện lấy token và password từ request.
    /// Nếu token hợp lệ thì thay đổi password dựa vào email ở trong token.
    String newPassword = requestPasswordDTO.getNewPassword();

    if (!StringUtils.hasText(token) || !StringUtils.hasText(newPassword)) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Token or new password is empty")
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
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Token is invalid")
              .build();
    }

    String email = claims.get("email").toString();
    Optional<Account> account = accountRepository.findAccountByEmail(email);
    if (account.isEmpty()) {
      return ResponseData.builder()
              .status(HttpStatus.NOT_FOUND.value())
              .message("Email not found")
              .build();
    }
    Account saved = account.get();
    saved.setPassword(passwordEncoder.encode(newPassword));
    try {
      accountRepository.save(saved);
    } catch (RuntimeException e) {
      return ResponseData.builder()
              .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message("Error occurred while updating password")
              .build();
    }
    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Password reset successfully")
            .build();
  }

  /// Hàm thực hiện refresh token.
  public ResponseData refreshToken(String token) {
    if (!StringUtils.hasText(token)) {
      return ResponseData.builder()
              .status(HttpStatus.BAD_REQUEST.value())
              .message("Token is empty")
              .build();
    }

    Claims claims;
    try {
      claims = Jwts.parserBuilder()
              .setSigningKey(Keys.hmacShaKeyFor(refreshToken.getBytes(StandardCharsets.UTF_8)))
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (RuntimeException e) {
      return ResponseData.builder()
              .status(HttpStatus.UNAUTHORIZED.value())
              .message("Token is invalid")
              .build();
    }

    String accountId = claims.get("account_id").toString();
    Optional<Account> account = accountRepository.findAccountById(accountId);
    if (account.isEmpty()) {
      return ResponseData.builder()
              .status(HttpStatus.NOT_FOUND.value())
              .message("Account not found")
              .build();
    }
    Account saved = account.get();
    SecretKey secretKey = Keys.hmacShaKeyFor(accessToken.getBytes(StandardCharsets.UTF_8));
    String newAccessToken = Jwts.builder()
            .setIssuer("auth-service")
            .setSubject("Access Token")
            .addClaims(Map.of("account_id", saved.getId(), "role", saved.getRole().toString()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration)) ///  12 hour.
            .setIssuedAt(new Date())
            .signWith(secretKey)
            .compact();

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Refresh token successfully")
            .data(Map.of("accessToken", newAccessToken))
            .build();
  }

  public ResponseData logout(String token) {
    System.out.println("Received access token for logout: " + token);

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
    System.out.println("Token received for authentication: " + token);

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
    } catch (RuntimeException e) {
      return AuthResponse.newBuilder()
              .setIsValid(false)
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
}