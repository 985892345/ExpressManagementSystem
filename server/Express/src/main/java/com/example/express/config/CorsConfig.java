package com.example.express.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决前后端分离导致的跨域问题
 *
 * @author 985892345
 * 2022/12/12 15:00
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOriginPatterns("*")
      .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
      .maxAge(16800)
      .allowedHeaders("*")
      .allowCredentials(true);
  }
}
