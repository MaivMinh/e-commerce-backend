package com.minh.file_service.controller;

import com.minh.file_service.response.ResponseData;
import com.minh.file_service.service.ImageProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/files")
public class FileProcessingController {
  private final ImageProcessingService imageProcessingService;

  @PostMapping(value = "/images/upload")
  public ResponseEntity<ResponseData> uploadImage(@RequestParam("image") MultipartFile image) {
    ResponseData response = imageProcessingService.uploadImage(image);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
