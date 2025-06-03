package com.minh.promotion_service.command.controller;

import com.minh.promotion_service.DTOs.PromotionDTO;
import com.minh.promotion_service.command.commands.CreatePromotionCommand;
import com.minh.promotion_service.command.commands.DeletePromotionCommand;
import com.minh.promotion_service.command.commands.UpdatePromotionCommand;
import com.minh.promotion_service.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/promotions")
@Validated
@RequiredArgsConstructor
public class PromotionCommandController {
  private final CommandGateway commandGateway;

  @PostMapping(value = "")
  public ResponseEntity<ResponseData> createPromotion(@RequestBody @Valid PromotionDTO promotionDTO) {
    CreatePromotionCommand command = CreatePromotionCommand.builder()
            .promotionId(promotionDTO.getId())
            .code(promotionDTO.getCode())
            .type(promotionDTO.getType())
            .discountValue(promotionDTO.getDiscountValue())
            .minOrderValue(promotionDTO.getMinOrderValue())
            .startDate(promotionDTO.getStartDate())
            .endDate(promotionDTO.getEndDate())
            .usageLimit(promotionDTO.getUsageLimit())
            .usageCount(promotionDTO.getUsageCount())
            .status(promotionDTO.getStatus())
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(ResponseData.builder().message("Successfully created promotion").build());
  }

  @PutMapping(value = "")
  public ResponseEntity<ResponseData> updatePromotion(@RequestBody @Valid PromotionDTO promotionDTO) {
    UpdatePromotionCommand command = UpdatePromotionCommand.builder()
            .promotionId(promotionDTO.getId())
            .code(promotionDTO.getCode())
            .type(promotionDTO.getType())
            .discountValue(promotionDTO.getDiscountValue())
            .minOrderValue(promotionDTO.getMinOrderValue())
            .startDate(promotionDTO.getStartDate())
            .endDate(promotionDTO.getEndDate())
            .usageLimit(promotionDTO.getUsageLimit())
            .usageCount(promotionDTO.getUsageCount())
            .status(promotionDTO.getStatus())
            .build();

    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.OK.value()).body(ResponseData.builder().message("Successfully updated promotion").build());
  }

  @DeleteMapping(value = "/{promotionId}")
  public ResponseEntity<ResponseData> deletePromotion(@PathVariable String promotionId) {
    DeletePromotionCommand command = DeletePromotionCommand.builder()
            .promotionId(promotionId)
            .build();
    commandGateway.sendAndWait(command, 15000, TimeUnit.MILLISECONDS);
    return ResponseEntity.status(HttpStatus.OK.value()).body(ResponseData.builder().message("Successfully deleted promotion with ID: " + promotionId).build());
  }
}