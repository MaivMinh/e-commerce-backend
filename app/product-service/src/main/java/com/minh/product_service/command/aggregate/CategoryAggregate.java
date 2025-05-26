package com.minh.product_service.command.aggregate;

import com.minh.product_service.command.commands.CreateCategoryCommand;
import com.minh.product_service.command.commands.DeleteCategoryCommand;
import com.minh.product_service.command.commands.UpdateCategoryCommand;
import com.minh.product_service.command.events.CategoryCreatedEvent;
import com.minh.product_service.command.events.CategoryDeletedEvent;
import com.minh.product_service.command.events.CategoryUpdatedEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Slf4j
@Aggregate
@Setter
@Getter
public class CategoryAggregate {
  @AggregateIdentifier
  private String id;
  private String parentId;
  private String name;
  private String description;
  private String errorMsg;

  public CategoryAggregate() {
    /// Default constructor is required by Axon Framework.
  }

  @CommandHandler
  public CategoryAggregate(CreateCategoryCommand command) {
    /// Create new events.
    CategoryCreatedEvent event = new CategoryCreatedEvent();
    BeanUtils.copyProperties(command, event);
    /// Apply events to the aggregate.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CategoryCreatedEvent event) {
    this.id = event.getId();
    this.parentId = event.getParentId();
    this.name = event.getName();
    this.description = event.getDescription();
  }

  @CommandHandler
  public void handle(UpdateCategoryCommand command) {
    CategoryUpdatedEvent event = new CategoryUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CategoryUpdatedEvent event) {
    /// Khi thực hiện update thì không phải truyền id vào. Vì ID không được phép thay đổi.
    this.name = event.getName();
    this.parentId = event.getParentId();
    this.description = event.getDescription();
  }


  @CommandHandler
  public void handle(DeleteCategoryCommand command) {
    /// Tạo sự kiện xóa danh mục sản phẩm.
    CategoryDeletedEvent event = new CategoryDeletedEvent();
    BeanUtils.copyProperties(command, event);
    /// Áp dụng sự kiện xóa danh mục sản phẩm.
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CategoryDeletedEvent event) {
    /// Xóa danh mục sản phẩm.
    this.id = null;
    this.name = null;
    this.parentId = null;
    this.description = null;
    this.errorMsg = null;
  }
}
