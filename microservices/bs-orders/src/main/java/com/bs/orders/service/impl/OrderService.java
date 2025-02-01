package com.bs.orders.service.impl;

import static lombok.AccessLevel.PRIVATE;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.orders.service.IOrderService;
import org.springframework.stereotype.Service;
import com.bs.orders.repository.IOrderRepository;
import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {

  IOrderRepository repository;

  @Override
  public OrderResponse create(OrderRequest orderRequest) {
    return repository.create(orderRequest);
  }
}
