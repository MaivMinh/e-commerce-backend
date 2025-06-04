package com.minh.promotion_service.query.handler;

import com.minh.promotion_service.query.queries.FindAllPromotionsQuery;
import com.minh.promotion_service.query.queries.FindPromotionQuery;
import com.minh.promotion_service.response.ResponseData;
import com.minh.promotion_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PromotionQueryHandler {
  private final PromotionService promotionService;

  @QueryHandler
  public ResponseData handle(FindAllPromotionsQuery query) {
    return promotionService.findAllPromotions(query);
  }

  @QueryHandler
  public ResponseData handle(FindPromotionQuery query) {
    return promotionService.findPromotionById(query.getPromotionId());
  }
}
