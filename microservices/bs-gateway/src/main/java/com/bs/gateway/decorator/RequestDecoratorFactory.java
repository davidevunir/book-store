package com.bs.gateway.decorator;

import static jakarta.ws.rs.HttpMethod.GET;
import static jakarta.ws.rs.HttpMethod.PUT;
import static jakarta.ws.rs.HttpMethod.POST;
import static jakarta.ws.rs.HttpMethod.PATCH;
import static jakarta.ws.rs.HttpMethod.DELETE;

import lombok.RequiredArgsConstructor;
import com.bs.gateway.model.GatewayRequest;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

@Component
@RequiredArgsConstructor
public class RequestDecoratorFactory {

  private final ObjectMapper objectMapper;

  public ServerHttpRequestDecorator getDecorator(GatewayRequest request) {
    return switch (request.getTargetMethod().name().toUpperCase()) {
      case GET -> new GetRequestDecorator(request);//No se envía objectMapper porque GET no debería tener body
      case POST -> new PostRequestDecorator(request, objectMapper);
      case PUT -> new PutRequestDecorator(request, objectMapper);
      case PATCH -> new PatchRequestDecorator(request, objectMapper);
      case DELETE -> new DeleteRequestDecorator(request);//No se envía objectMapper porque DELETE no debería tener body
      default -> throw new IllegalArgumentException("Invalid http method");
    };
  }
}
