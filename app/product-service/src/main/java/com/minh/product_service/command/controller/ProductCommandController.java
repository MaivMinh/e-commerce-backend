package com.minh.product_service.command.controller;

import com.minh.product_service.command.commands.CreateProductCommand;
import com.minh.product_service.command.commands.DeleteProductCommand;
import com.minh.product_service.command.commands.UpdateProductCommand;
import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
@RequestMapping(value = "/api/products", produces = {"application/json"})
@RequiredArgsConstructor
public class ProductCommandController {
  private final CommandGateway commandGateway;

  /// ================== ADMIN ROLE ================== ///

  /// Hàm thực hiện tạo mới một sản phẩm.
  /// DONE!
  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createProduct(@Valid @RequestBody ProductDTO productDTO) {
    CreateProductCommand command = CreateProductCommand.builder()
            .id(UUID.randomUUID().toString())
            .name(productDTO.getName())
            .description(productDTO.getDescription())
            .cover(productDTO.getCover())
            .build();
    commandGateway.sendAndWait(command);
    return ResponseEntity.ok(new ResponseData(HttpStatus.CREATED.value(), "Product is created successfully", null));
  }


  /// Hàm thực hiện cập nhật thông tin một sản phẩm.
  /// DONE!
  @PutMapping(value = "")
  public ResponseEntity<ResponseData> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
    UpdateProductCommand command = UpdateProductCommand.builder()
            .id(productDTO.getId())
            .name(productDTO.getName())
            .description(productDTO.getDescription())
            .cover(productDTO.getCover())
            .build();
    commandGateway.sendAndWait(command);
    return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Product is updated successfully", null));
  }

  /// Hàm thực hiện xóa một sản phẩm.
  /// DONE!
  @DeleteMapping(value = "/{productId}")
  public ResponseEntity<ResponseData> deleteProduct(@PathVariable String productId) {
    DeleteProductCommand command = DeleteProductCommand.builder()
            .id(productId)
            .build();
    commandGateway.sendAndWait(command, 10000, TimeUnit.MILLISECONDS);
    return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), "Product is deleted successfully", null));
  }
}
