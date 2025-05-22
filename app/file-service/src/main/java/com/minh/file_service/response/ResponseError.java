package com.minh.file_service.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseError {
  @JsonIgnore
  private int status;
  private String message;
  private String apiPath;
  private LocalDateTime errorTime;
}
