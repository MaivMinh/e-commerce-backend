package com.minh.order_service.query.handler;

import com.minh.order_service.query.queries.FindAllOrdersQuery;
import com.minh.order_service.response.ResponseData;
import com.minh.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderQueryHandler {
  private final OrderService orderService;

  @QueryHandler
  public ResponseData on(FindAllOrdersQuery query) throws Exception {
    return orderService.findAllOrders(query);
  }
}
