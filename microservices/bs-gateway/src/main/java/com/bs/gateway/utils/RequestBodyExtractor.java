package com.bs.gateway.utils;

import static lombok.AccessLevel.PRIVATE;
import static reactor.core.publisher.Flux.just;
import static reactor.core.publisher.Flux.defer;
import static org.bouncycastle.util.Strings.fromUTF8ByteArray;
import static org.springframework.core.io.buffer.DataBufferUtils.retain;
import static org.springframework.core.io.buffer.DataBufferUtils.release;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.gateway.model.GatewayRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RequestBodyExtractor {

  ObjectMapper objectMapper;

  private static final String CHUNKED = "chunked";

  @SneakyThrows
  public GatewayRequest getRequest(ServerWebExchange exchange, DataBuffer buffer) {
    retain(buffer);
    var rawRequest = getRawRequest(defer(() -> just(buffer.split(buffer.readableByteCount()))));
    release(buffer);
    var request = objectMapper.readValue(rawRequest, GatewayRequest.class);
    request.setExchange(exchange);

    var headers = new HttpHeaders();
    headers.putAll(exchange.getRequest().getHeaders());
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    headers.set(HttpHeaders.TRANSFER_ENCODING, CHUNKED);
    request.setHeaders(headers);

    return request;
  }

  private String getRawRequest(Flux<DataBuffer> body) {
    AtomicReference<String> rawRef = new AtomicReference<>();
    body.subscribe(buffer -> {
      var bytes = new byte[buffer.readableByteCount()];
      buffer.read(bytes);
      rawRef.set(fromUTF8ByteArray(bytes));
    });

    return rawRef.get();
  }
}
