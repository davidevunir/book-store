package com.bs.gateway.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
class CorsConfig {

  @Bean
  public CorsWebFilter corsWebFilter() {
    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
    corsConfig.setAllowedOrigins(List.of("http://localhost:5173"));
    corsConfig.setMaxAge(3600L);
    corsConfig.setAllowedMethods(List.of("POST"));
    corsConfig.setAllowedHeaders(List.of("*"));
    corsConfig.setAllowCredentials(true);

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}