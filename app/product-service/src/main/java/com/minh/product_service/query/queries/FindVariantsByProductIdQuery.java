package com.minh.product_service.query.queries;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FindVariantsByProductIdQuery {
    private String productId;
}
