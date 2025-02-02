package com.bs.gateway.decorator;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import java.net.URI;
import lombok.NonNull;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpMethod;
import com.bs.gateway.model.GatewayRequest;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PutRequestDecorator extends ServerHttpRequestDecorator {

  ObjectMapper objectMapper;
  GatewayRequest gatewayRequest;

  public PutRequestDecorator(GatewayRequest gatewayRequest, ObjectMapper objectMapper) {
    super(gatewayRequest.getExchange().getRequest());
    this.gatewayRequest = gatewayRequest;
    this.objectMapper = objectMapper;
  }

  @Override
  @NonNull
  public HttpMethod getMethod() {
    return PUT;
  }

  @Override
  @NonNull
  public URI getURI() {
    return fromUri((URI) gatewayRequest.getExchange().getAttributes().get(GATEWAY_REQUEST_URL_ATTR)).build().toUri();
  }

  @Override
  @NonNull
  public HttpHeaders getHeaders() {
    return gatewayRequest.getHeaders();
  }

  @Override
  @NonNull
  @SneakyThrows
  public Flux<DataBuffer> getBody() {
    var bufferFactory = new DefaultDataBufferFactory();
    byte[] bodyData = objectMapper.writeValueAsBytes(gatewayRequest.getBody());
    var buffer = bufferFactory.allocateBuffer(bodyData.length);
    buffer.write(bodyData);

    return Flux.just(buffer);
  }
}
