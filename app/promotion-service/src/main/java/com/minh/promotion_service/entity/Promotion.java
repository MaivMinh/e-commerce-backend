package com.minh.promotion_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Promotion extends BaseEntity {
  @Id
  private String id;
  private String code;
  private String type;
  private Double discountValue;
  private Double minOrderValue; /// Khi người dùng load đơn hàng trong checkout, thì sẽ fetch promotion nào có minOrderValue <= tổng giá trị đơn hàng
  private Timestamp startDate;
  private Timestamp endDate;
  private Integer usageLimit;
  private Integer usageCount; // Số lần đã sử dụng promotion này
  private PromotionStatus status; // Trạng thái của promotion (ACTIVE, INACTIVE)
}
