package com.example.express.config;

import com.example.express.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 23:27
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new TokenInterceptor())
      .addPathPatterns("/**") // 拦截所有请求
      .excludePathPatterns("/error")
      .excludePathPatterns("/account/**"); // 排除 /account/** 以下的请求
  }
}
