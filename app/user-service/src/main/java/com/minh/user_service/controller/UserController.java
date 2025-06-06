package com.minh.user_service.controller;

import com.minh.user_service.DTOs.UserCreateDTO;
import com.minh.user_service.DTOs.UserDTO;
import com.minh.user_service.response.ResponseData;
import com.minh.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/users")
public class UserController {
  private final UserService userService;

  @GetMapping(value = "")
  public ResponseEntity<ResponseData> findAllUsers(
          @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
          @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
          @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

    page = (page > 0) ? (page - 1) : 0;
    size = (size > 0) ? size : 10;

    ResponseData response = userService.findAllUsers(page, size, sort);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
    ResponseData response = userService.createUser(userCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping(value = "/{userId}")
  public ResponseEntity<ResponseData> getUserById(@PathVariable String userId) {
    ResponseData response = userService.getUserById(userId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping("/profile")
  public ResponseEntity<ResponseData> getProfile(HttpServletRequest request) {
    String accountId = request.getHeader("ACCOUNT-ID");
    if (!StringUtils.hasText(accountId)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(new ResponseData(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", null));
    }
    ResponseData response = userService.getProfile(accountId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @PutMapping("")
  public ResponseEntity<ResponseData> updateUser(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) {
    ResponseData response = userService.updateUser(userDTO);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<ResponseData> deleteUser(@PathVariable String userId) {
    ResponseData response = userService.deleteUser(userId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}