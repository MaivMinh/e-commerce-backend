package com.minh.product_service.query.queries;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FindVariantsQuery {
    private Integer size;
    private Integer page;
    private String sort;
}
