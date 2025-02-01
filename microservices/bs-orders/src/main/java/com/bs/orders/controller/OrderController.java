package com.bs.orders.controller;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.badRequest;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.orders.service.IOrderService;
import org.springframework.http.ResponseEntity;
import com.bs.orders.repository.dto.request.OrderRequest;
import org.springframework.web.bind.annotation.GetMapping;
import com.bs.orders.repository.dto.response.OrderResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderController {

  IOrderService service;

  @GetMapping
  public ResponseEntity<List<OrderResponse>> getAll() {
    return ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getById(@PathVariable("id") UUID id) {
    var response = service.getById(id);

    return response == null ? notFound().build() : ok(response);
  }

  @PostMapping
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest order) {
    var response = service.create(order);

    return response == null ? badRequest().build() : status(CREATED).body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<OrderResponse> update(@PathVariable("id") UUID id, @Valid @RequestBody String status) {
    var response = service.updateStatus(id, status);

    return response == null ? notFound().build() : ok(response);
  }
}
