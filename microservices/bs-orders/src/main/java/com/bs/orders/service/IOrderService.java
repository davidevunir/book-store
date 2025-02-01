package com.bs.orders.service;

import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;
import java.util.List;
import java.util.UUID;

public interface IOrderService {

  List<OrderResponse> getAll();

  OrderResponse getById(UUID id);

  OrderResponse create(OrderRequest order);

  OrderResponse updateStatus(UUID id, String status);
}
