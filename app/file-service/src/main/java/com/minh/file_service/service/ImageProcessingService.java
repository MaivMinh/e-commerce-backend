package com.minh.file_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.minh.file_service.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageProcessingService {
  private final Cloudinary cloudinary;

  public ResponseData uploadImage(MultipartFile image) {
    try {
      Map uploadResult = cloudinary.uploader().upload(image.getBytes(),
              ObjectUtils.asMap("resource_type", "auto"));
      String secureUrl = (String) uploadResult.get("secure_url");
      return ResponseData.builder()
              .status(HttpStatus.CREATED.value())
              .message("Image uploaded successfully")
              .data(Map.of("url", secureUrl))
              .build();
    } catch (Exception e) {
      throw new RuntimeException("Image upload failed", e);
    }
  }
}
