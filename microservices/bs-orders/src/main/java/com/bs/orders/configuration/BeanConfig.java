package com.bs.orders.configuration;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public ObjectMapper objectMapper() {
    var mapper = new ObjectMapper();
    mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.registerModule(new JavaTimeModule());

    return mapper;
  }
}
