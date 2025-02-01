package com.bs.orders.service.impl;

import static lombok.AccessLevel.PRIVATE;
import static com.bs.orders.utils.Constants.STATUS;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.orders.service.IOrderService;
import org.springframework.stereotype.Service;
import com.bs.orders.repository.IOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bs.orders.repository.dto.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bs.orders.repository.dto.response.OrderResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {

  ObjectMapper objectMapper;
  IOrderRepository repository;

  @Override
  public List<OrderResponse> getAll() {
    return repository.getAll();
  }

  @Override
  public OrderResponse getById(UUID id) {
    return repository.getOrderResponseById(id);
  }

  @Override
  public OrderResponse create(OrderRequest orderRequest) {
    return repository.create(orderRequest);
  }

  @Override
  public OrderResponse updateStatus(UUID id, String statusJson) {
    if (id == null || isBlank(statusJson)) {
      return null;
    }

    var order = repository.getById(id);
    if (order == null) {
      return null;
    }

    try {
      order.setStatus(objectMapper.readTree(statusJson).get(STATUS).asText());
      return repository.save(order);
    } catch (JsonProcessingException e) {
      log.error("Error al actualizar el estado del pedido con id: {} - {}", id, e.getMessage());
      return null;
    }
  }
}
