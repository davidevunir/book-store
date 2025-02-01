package com.bs.orders.repository;

import java.util.List;
import java.util.UUID;
import com.bs.orders.repository.model.Order;
import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;

public interface IOrderRepository {

  List<OrderResponse> getAll();

  OrderResponse getOrderResponseById(UUID id);

  OrderResponse create(OrderRequest order);

  Order getById(UUID id);

  OrderResponse save(Order order);
}
