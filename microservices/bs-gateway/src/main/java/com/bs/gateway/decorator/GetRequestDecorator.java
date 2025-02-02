package com.bs.gateway.decorator;

import static lombok.AccessLevel.PRIVATE;
import static reactor.core.publisher.Flux.empty;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import java.net.URI;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpMethod;
import com.bs.gateway.model.GatewayRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class GetRequestDecorator extends ServerHttpRequestDecorator {

  GatewayRequest gatewayRequest;

  public GetRequestDecorator(GatewayRequest gatewayRequest) {
    super(gatewayRequest.getExchange().getRequest());
    this.gatewayRequest = gatewayRequest;
  }

  @Override
  @NonNull
  public HttpMethod getMethod() {
    return GET;
  }

  @Override
  @NonNull
  public URI getURI() {
    var uri = (URI) gatewayRequest.getExchange().getAttributes().get(GATEWAY_REQUEST_URL_ATTR);

    return fromUri(uri).queryParams(gatewayRequest.getQueryParams()).build().toUri();
  }

  @Override
  @NonNull
  public HttpHeaders getHeaders() {
    return gatewayRequest.getHeaders();
  }

  @Override
  @NonNull
  public Flux<DataBuffer> getBody() {
    return empty();
  }
}
