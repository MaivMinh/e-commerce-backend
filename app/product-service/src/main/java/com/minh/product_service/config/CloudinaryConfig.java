package com.minh.product_service.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {
  private final Environment env;

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", env.getProperty("CLOUDINARY_CLOUD_NAME"),
            "api_key", env.getProperty("CLOUDINARY_API_KEY"),
            "api_secret", env.getProperty("CLOUDINARY_API_SECRET")));
  }
}
