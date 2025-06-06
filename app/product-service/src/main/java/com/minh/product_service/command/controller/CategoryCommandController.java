package com.minh.product_service.command.controller;

import com.minh.product_service.command.commands.CreateCategoryCommand;
import com.minh.product_service.command.commands.DeleteCategoryCommand;
import com.minh.product_service.command.commands.UpdateCategoryCommand;
import com.minh.product_service.dto.CategoryCreateDTO;
import com.minh.product_service.dto.CategoryDTO;
import com.minh.product_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories", produces = {"application/json"})
public class CategoryCommandController {
  private final CommandGateway commandGateway;

  /// =========================== ADMIN ROLE =========================== ///
  /// Hàm tạo mới danh mục sản phẩm.
  /// DONE!!!
  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createCategory(@RequestBody @Valid CategoryCreateDTO categoryCreateDTO) {
    CreateCategoryCommand command = CreateCategoryCommand.builder()
            .id(UUID.randomUUID().toString())
            .parentId(categoryCreateDTO.getParentId())
            .name(categoryCreateDTO.getName())
            .description(categoryCreateDTO.getDescription())
            .slug(categoryCreateDTO.getSlug())
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ResponseData(200, "Success", null));
  }

  /// Hàm cập nhật danh mục sản phẩm.
  /// DONE.
  @PutMapping(value = "")
  public ResponseEntity<ResponseData> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
    UpdateCategoryCommand command = UpdateCategoryCommand.builder()
            .id(categoryDTO.getId())
            .parentId(categoryDTO.getParentId())
            .name(categoryDTO.getName())
            .description(categoryDTO.getDescription())
            .slug(categoryDTO.getSlug())
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.OK.value()).body(new ResponseData(200, "Category is updated successfully!", null));
  }

  /// Hàm xóa danh mục sản phẩm.
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<ResponseData> deleteCategory(@PathVariable("id") String id) {
    DeleteCategoryCommand command = DeleteCategoryCommand.builder()
            .id(id)
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.OK.value()).body(new ResponseData(200, "Category is deleted successfully!", null));
  }

}