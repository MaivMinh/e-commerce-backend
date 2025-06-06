package com.minh.product_service.query.handler;

import com.minh.product_service.query.queries.FetchCategoriesQuery;
import com.minh.product_service.query.queries.FetchCategoryQuery;
import com.minh.product_service.query.queries.FindAllCategoriesQuery;
import com.minh.product_service.query.queries.SearchCategoriesQuery;
import com.minh.product_service.response.ResponseData;
import com.minh.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryQueryHandler {
  private final CategoryService categoryService;

  @QueryHandler
  public ResponseData handle(FetchCategoriesQuery query) {
    return categoryService.fetchCategories(query.getPage(), query.getSize(), query.getSort());
  }

  @QueryHandler
  public ResponseData handle(FindAllCategoriesQuery query) {
    return categoryService.findAllCategories(query);
  }

  @QueryHandler
  public ResponseData handle(FetchCategoryQuery query) {
    return categoryService.fetchCategory(query.getId());
  }
}