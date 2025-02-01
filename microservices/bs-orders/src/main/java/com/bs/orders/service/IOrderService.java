package com.bs.orders.service;

import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;

public interface IOrderService {

  OrderResponse create(OrderRequest order);
}
