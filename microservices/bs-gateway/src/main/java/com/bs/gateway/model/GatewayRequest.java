package com.bs.gateway.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ServerWebExchange;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class GatewayRequest {

  HttpMethod targetMethod;
  LinkedMultiValueMap<String, String> queryParams;
  Object body;

  @JsonIgnore
  ServerWebExchange exchange;

  @JsonIgnore
  HttpHeaders headers;
}