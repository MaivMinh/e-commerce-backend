package com.minh.product_service.command.controller;

import com.minh.product_service.command.commands.CreateProductCommand;
import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/api/products", produces = {"application/json"})
@RequiredArgsConstructor
public class ProductCommandController {
  private final CommandGateway commandGateway;

  /**
    This block use for ADMIN.
   */
  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createProduct(@Valid @RequestBody ProductDTO productDTO) {
    CreateProductCommand command = CreateProductCommand.builder()
        .id(productDTO.getId())
        .name(productDTO.getName())
        .description(productDTO.getDescription())
        .build();

    return ResponseEntity.ok(new ResponseData(200, "Success", null));
  }

  /**
   This block use for USER.
   */
  @GetMapping(value = "/{productId}")
  public ResponseEntity<ResponseData> getProduct(@PathVariable Long productId) {
    return ResponseEntity.ok(new ResponseData(200, "Success", null));
  }
}
