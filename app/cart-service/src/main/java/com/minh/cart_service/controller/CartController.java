package com.minh.cart_service.controller;

import com.minh.cart_service.DTOs.CartItemDTO;
import com.minh.cart_service.response.ResponseData;
import com.minh.cart_service.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/api/carts", produces = {"application/json"})
@RequiredArgsConstructor
public class CartController {
  private final CartItemService cartService;

  /// Hàm thực hiện tạ một mục trong giỏ hàng của người dùng
  /// DONE
  @PostMapping(value = "/{accountId}/items")
  public ResponseEntity<ResponseData> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO,
                                                     @PathVariable String accountId) {
    ResponseData response = cartService.createCartItem(accountId, cartItemDTO);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
  }

  /// Hàm thực hiện cập nhật một mục trong giỏ hàng của người dùng
  /// DONE
  @PutMapping(value = "/items/{id}")
  public ResponseEntity<ResponseData> updateCartItem(@PathVariable String id, @RequestBody @Valid CartItemDTO cartItemDTO) {
    cartItemDTO.setId(id);
    ResponseData response = cartService.updateCartItem(cartItemDTO);
    return ResponseEntity.status(HttpStatus.OK.value()).body(response);
  }

  /// Hàm thực hiện xóa một mục trong giỏ hàng của người dùng
  /// DONE
  @DeleteMapping(value = "/items/{id}")
  public ResponseEntity<ResponseData> deleteCartItem(@PathVariable String id) {
    ResponseData response = cartService.deleteCartItem(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
  }

  /// Hàm thực hiện lấy thông tin giỏ hàng, bao gồm thông tin chung cũng như các mục trong giỏ hàng của người dùng.
  /// DONE.
  @GetMapping(value = "/{accountId}")
  public ResponseEntity<ResponseData> findCartByAccountId(@PathVariable String accountId) {
    ResponseData response = cartService.findCartByAccountId(accountId);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping(value = "/info")
  public ResponseEntity<ResponseData> getCartServiceInfo() {
    ResponseData response = cartService.getCartServiceInfo();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}