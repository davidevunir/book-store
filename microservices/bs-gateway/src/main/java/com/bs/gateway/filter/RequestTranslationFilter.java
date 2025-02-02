package com.bs.gateway.filter;

import static jakarta.ws.rs.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.core.io.buffer.DataBufferUtils.join;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.bs.gateway.utils.RequestBodyExtractor;
import org.springframework.web.server.ServerWebExchange;
import com.bs.gateway.decorator.RequestDecoratorFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestTranslationFilter implements GlobalFilter {

  private final RequestBodyExtractor requestBodyExtractor;
  private final RequestDecoratorFactory requestDecoratorFactory;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    exchange.getResponse().setStatusCode(BAD_REQUEST);

    if (exchange.getRequest().getHeaders().getContentType() == null || !exchange.getRequest().getMethod().matches(POST)) {
      log.info("Request does not have a content type or is not a POST request");
      return exchange.getResponse().setComplete();
    } else {
      return join(exchange.getRequest().getBody())
          .flatMap(dataBuffer -> {
            var request = requestBodyExtractor.getRequest(exchange, dataBuffer);
            var mutatedRequest = requestDecoratorFactory.getDecorator(request);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, mutatedRequest.getURI());
            if (request.getQueryParams() != null) {
              request.getQueryParams().clear();
            }
            log.info("Proxying request: {} {}", mutatedRequest.getMethod(), mutatedRequest.getURI());
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
          });
    }
  }
}
