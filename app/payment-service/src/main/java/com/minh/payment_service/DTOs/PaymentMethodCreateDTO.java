package com.minh.payment_service.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodCreateDTO {
  private String paymentMethodId;
  @NotEmpty(message = "Code cannot be empty")
  private String code;
  @NotEmpty(message = "Name cannot be empty")
  private String name;
  private String description;
  @NotEmpty(message = "Type cannot be empty")
  private String type;
  @NotEmpty(message = "Provider cannot be empty")
  private String provider;
  @NotEmpty(message = "Currency cannot be empty")
  private String currency;
  @NotNull(message = "Icon URL cannot be null")
  private Boolean isActive;
  private String iconUrl;
}
