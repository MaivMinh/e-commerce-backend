package com.minh.product_service.query.projection;

import com.minh.product_service.command.events.CategoryCreatedEvent;
import com.minh.product_service.command.events.CategoryDeletedEvent;
import com.minh.product_service.command.events.CategoryUpdatedEvent;
import com.minh.product_service.entity.Category;
import com.minh.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group")
public class CategoryProjection {
  private final CategoryService categoryService;

  @EventHandler
  public void on(CategoryCreatedEvent event) {
    Category category = new Category();
    BeanUtils.copyProperties(event, category);
    categoryService.createCategory(category);
  }

  @EventHandler
  public void on(CategoryUpdatedEvent event) {
    categoryService.updateCategory(event);
  }

  @EventHandler
  public void on(CategoryDeletedEvent event) {
    categoryService.deleteCategory(event);
  }
}
