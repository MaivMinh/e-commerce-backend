package com.minh.review_service.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData {
  @JsonIgnore
  private int status;
  private String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)  // Khi data = null thì sẽ không có trong Response.
  private Object data;

  public ResponseData(int status, String message) {
    this.status = status;
    this.message = message;
    data = null;
  }
}
