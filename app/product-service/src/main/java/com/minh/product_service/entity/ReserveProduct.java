package com.minh.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.axonframework.modelling.command.EntityId;

import java.sql.Timestamp;

@Entity
@Table(name = "reserve_products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveProduct extends BaseEntity {
  @Id
  private String id;
  private String productVariantId;
  private String orderId;
  private Integer quantity;
  private Timestamp reserveAt;
}
