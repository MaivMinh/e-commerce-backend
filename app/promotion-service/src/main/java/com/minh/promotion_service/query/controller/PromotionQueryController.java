package com.minh.promotion_service.query.controller;

import com.minh.promotion_service.query.queries.FindAllPromotionsQuery;
import com.minh.promotion_service.query.queries.FindPromotionQuery;
import com.minh.promotion_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/promotions")
@Validated
@RequiredArgsConstructor
public class PromotionQueryController {
  private final QueryGateway queryGateway;

  @GetMapping("")
  public ResponseEntity<ResponseData> findAllPromotions(@RequestParam(required = true, defaultValue = "active") String status) {
    FindAllPromotionsQuery query = new FindAllPromotionsQuery(status);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping(value = "/{promotionId}")
  public ResponseEntity<ResponseData> findPromotionById(@PathVariable String promotionId) {
    // Implement the logic to find a promotion by its ID
    // This is a placeholder for the actual implementation
    FindPromotionQuery query = new FindPromotionQuery(promotionId);
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
