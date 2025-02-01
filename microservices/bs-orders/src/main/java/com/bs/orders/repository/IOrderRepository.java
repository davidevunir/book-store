package com.bs.orders.repository;

import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;

public interface IOrderRepository {

  OrderResponse create(OrderRequest order);
}
