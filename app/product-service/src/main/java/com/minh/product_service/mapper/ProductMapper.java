package com.minh.product_service.mapper;


import com.minh.product_service.dto.ProductDTO;
import com.minh.product_service.entity.Product;
import com.minh.product_service.entity.ProductStatus;

public class ProductMapper {

  public static void mapToProductDTO(Product product, ProductDTO productDto) {
    productDto.setId(product.getId());
    productDto.setName(product.getName());
    productDto.setSlug(product.getSlug());
    productDto.setDescription(product.getDescription());
    productDto.setCover(product.getCover());
    productDto.setPrice(product.getPrice());
    productDto.setOriginalPrice(product.getOriginalPrice());
    productDto.setIsFeatured(product.getIsFeatured());
    productDto.setIsBestseller(product.getIsBestseller());
    productDto.setIsNew(product.getIsNew());
    productDto.setCategoryId(product.getCategoryId());
    productDto.setStatus(product.getStatus().toString());
    productDto.setSoldItems(product.getSoldItems());
  }

  public static void mapToProduct(ProductDTO productDTO, Product product) {
    product.setId(productDTO.getId());
    product.setName(productDTO.getName());
    product.setDescription(productDTO.getDescription());
  }
}