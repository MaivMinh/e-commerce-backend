package com.minh.promotion_service.service;

import com.minh.promotion_service.DTOs.PromotionDTO;
import com.minh.promotion_service.command.events.PromotionCreatedEvent;
import com.minh.promotion_service.command.events.PromotionDeletedEvent;
import com.minh.promotion_service.command.events.PromotionUpdatedEvent;
import com.minh.promotion_service.entity.Promotion;
import com.minh.promotion_service.entity.PromotionStatus;
import com.minh.promotion_service.mapper.PromotionMapper;
import com.minh.promotion_service.query.queries.FindAllPromotionsQuery;
import com.minh.promotion_service.query.queries.FindPromotionsWithParamsQuery;
import com.minh.promotion_service.repository.PromotionRepository;
import com.minh.promotion_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {
  private final PromotionRepository promotionRepository;

  public void createPromotion(PromotionCreatedEvent event) {
    // Convert the event to a promotion entity and save it to the repository.
    // Assuming you have a Promotion entity class that matches your database schema.
    Promotion promotion = new Promotion();
    promotion.setId(event.getPromotionId());
    promotion.setCode(event.getCode());
    promotion.setType(event.getType());
    promotion.setDiscountValue(event.getDiscountValue());
    promotion.setMinOrderValue(event.getMinOrderValue());
    promotion.setStartDate(event.getStartDate());
    promotion.setEndDate(event.getEndDate());
    promotion.setUsageLimit(event.getUsageLimit());
    promotion.setUsageCount(0);
    promotion.setStatus(PromotionStatus.valueOf(event.getStatus().toLowerCase(Locale.ROOT)));
    promotionRepository.save(promotion);
  }

  public void updatePromotion(PromotionUpdatedEvent event) {
    // Find the existing promotion by ID and update its fields.
    Promotion promotion = promotionRepository.findById(event.getPromotionId()).orElseThrow(() -> new RuntimeException("Promotion not found"));

    promotion.setCode(event.getCode());
    promotion.setType(event.getType());
    promotion.setDiscountValue(event.getDiscountValue());
    promotion.setMinOrderValue(event.getMinOrderValue());
    promotion.setStartDate(event.getStartDate());
    promotion.setEndDate(event.getEndDate());
    promotion.setUsageLimit(event.getUsageLimit());
    promotion.setUsageCount(event.getUsageCount());
    promotion.setStatus(PromotionStatus.valueOf(event.getStatus()));

    // Save the updated promotion to the repository.
    promotionRepository.save(promotion);
  }

  public ResponseData findAllPromotions(FindAllPromotionsQuery query) {
    List<Promotion> promotions = promotionRepository.findAll();
    List<PromotionDTO> dtos = promotions.stream().map(promotion -> {
      PromotionDTO dto = new PromotionDTO();
      PromotionMapper.mapToPromotionDTO(promotion, dto);
      return dto;
    }).collect(Collectors.toList());

    return ResponseData.builder().status(HttpStatus.OK.value()).message("Promotions retrieved successfully").data(dtos).build();
  }

  public ResponseData findPromotionById(String promotionId) {
    Promotion promotion = promotionRepository.findById(promotionId).orElseThrow(() -> new RuntimeException("Promotion not found"));

    PromotionDTO dto = new PromotionDTO();
    PromotionMapper.mapToPromotionDTO(promotion, dto);
    return ResponseData.builder().status(HttpStatus.OK.value()).message("Promotion retrieved successfully").data(dto).build();
  }

  public void deletePromotion(PromotionDeletedEvent event) {
    // Find the promotion by ID and delete it.
    Promotion promotion = promotionRepository.findById(event.getPromotionId()).orElseThrow(() -> new RuntimeException("Promotion not found"));
    // Delete the promotion from the repository.
    promotionRepository.delete(promotion);
  }

  public ResponseData findPromotionsWithParams(FindPromotionsWithParamsQuery query) {
    String status = query.getStatus();
    String search = query.getSearch();
    int page = query.getPage();
    int size = query.getSize();
    Pageable pageable = PageRequest.of(page, size);
    Page<Promotion> promotionPage = null;

    if (StringUtils.hasText(status) && StringUtils.hasText(search)) {
      promotionPage = promotionRepository.findByStatusAndCodeContainingIgnoreCase(PromotionStatus.valueOf(status), search, pageable);
    } else if (StringUtils.hasText(status)) {
      promotionPage = promotionRepository.findByStatus(PromotionStatus.valueOf(status), pageable);
    } else if (StringUtils.hasText(search)) {
      promotionPage = promotionRepository.findByCodeContainingIgnoreCase(search, pageable);
    } else {
      promotionPage = promotionRepository.findAll(pageable);
    }


    Map<String, Object> data = new HashMap<>();
    data.put("promotions", promotionPage.getContent().stream().map(promotion -> {
      PromotionDTO dto = new PromotionDTO();
      PromotionMapper.mapToPromotionDTO(promotion, dto);
      return dto;
    }).collect(Collectors.toList()));
    data.put("totalPages", promotionPage.getTotalPages());
    data.put("totalElements", promotionPage.getTotalElements());
    data.put("size", promotionPage.getSize());
    data.put("page", promotionPage.getNumber() + 1); // Convert to 1-based index

    return ResponseData.builder()
            .status(HttpStatus.OK.value())
            .message("Promotions retrieved successfully")
            .data(data)
            .build();
  }
}
