package com.bs.orders.controller;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.orders.service.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderController {

  IOrderService service;

  @PostMapping
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest order) {
    var response = service.create(order);

    return response == null ? badRequest().build() : status(CREATED).body(response);
  }
}
