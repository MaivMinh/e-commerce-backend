package com.minh.promotion_service.query.controller;

import com.minh.promotion_service.query.queries.FindAllPromotionsQuery;
import com.minh.promotion_service.query.queries.FindPromotionQuery;
import com.minh.promotion_service.query.queries.FindPromotionsWithParamsQuery;
import com.minh.promotion_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(value = "/api/promotions")
@Validated
@RequiredArgsConstructor
public class PromotionQueryController {
  private final QueryGateway queryGateway;

  @GetMapping("/all")
  public ResponseEntity<ResponseData> findAllPromotions() {
    FindAllPromotionsQuery query = new FindAllPromotionsQuery();
    ResponseData response = queryGateway.query(query, ResponseTypes.instanceOf(ResponseData.class)).join();
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @GetMapping("")
  public ResponseEntity<ResponseData> findPromotions(@RequestParam(required = false, defaultValue = "") String status,
                                                     @RequestParam(required = false, defaultValue = "") String search,
                                                     @RequestParam(required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(required = false, defaultValue = "10") Integer size) {

    page = page < 0 ? 0: page - 1; // Adjust page to be zero-based
    size = size < 1 ? 10 : size; // Ensure size is at least 1
    status = status.toLowerCase(Locale.ROOT);

    FindPromotionsWithParamsQuery query = new FindPromotionsWithParamsQuery(status, search, page, size);
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
