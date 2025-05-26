package com.minh.product_service.query.queries;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindVariantQuery {
    private String variantId;
}
