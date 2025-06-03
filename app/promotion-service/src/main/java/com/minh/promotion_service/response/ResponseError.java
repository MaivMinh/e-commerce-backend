package com.minh.promotion_service.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ResponseError{
  @JsonIgnore
  private int status;
  private String message;
  private String apiPath;
  private LocalDateTime errorTime;
}
