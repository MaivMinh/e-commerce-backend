package com.minh.product_service.mapper;


import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.entity.Product;

public class ProductMapper {

  public static void mapToProductDTO(Product product, ProductDTO productDto) {
    productDto.setId(product.getId());
    productDto.setName(product.getName());
    productDto.setDescription(product.getDescription());
    productDto.setCover(product.getCover());
  }

  public static void mapToProduct(ProductDTO productDTO, Product product) {
    product.setId(productDTO.getId());
    product.setName(productDTO.getName());
    product.setDescription(productDTO.getDescription());
    product.setCover(productDTO.getCover());
  }
}