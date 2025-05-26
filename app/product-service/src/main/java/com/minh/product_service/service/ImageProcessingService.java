package com.minh.product_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageProcessingService {
  private final Cloudinary cloudinary;

  public String uploadImage(MultipartFile image) {
    try {
      Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
              ObjectUtils.asMap("resource_type", "auto"));
      return (String) uploadResult.get("secure_url");
    } catch (Exception e) {
      throw new RuntimeException("Image upload failed", e);
    }
  }
}