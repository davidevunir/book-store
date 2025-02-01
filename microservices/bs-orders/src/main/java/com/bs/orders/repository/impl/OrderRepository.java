package com.bs.orders.repository.impl;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.UUID;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.bs.orders.helper.OrderHelper;
import lombok.experimental.FieldDefaults;
import com.bs.orders.repository.model.Order;
import com.bs.orders.repository.IOrderRepository;
import org.springframework.stereotype.Repository;
import com.bs.orders.repository.jpa.OrderJpaRepository;
import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.response.OrderResponse;
import com.bs.orders.repository.dto.request.OrderDetailRequest;
import com.bs.orders.repository.dto.response.OrderDetailResponse;

@Slf4j
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderRepository implements IOrderRepository {

  OrderHelper orderHelper;
  OrderJpaRepository jpaRepository;

  private OrderResponse buildOrderResponse(Order order) {
    return OrderResponse.builder()
        .idBookOrder(order.getId())
        .idClient(order.getIdClient())
        .createdAt(order.getCreatedAt())
        .totalAmount(order.getTotalAmount())
        .status(order.getStatus())
        .orderDetailResponse(order.getOrderDetails().stream()
            .map(detail -> OrderDetailResponse.builder()
                .idOrderDetail(detail.getId())
                .idBook(detail.getIdBook())
                .quantity(detail.getQuantity())
                .price(detail.getPrice())
                .subtotal(detail.getSubtotal())
                .build()).toList())
        .build();
  }

  @Override
  public List<OrderResponse> getAll() {
    return jpaRepository.findAll()
        .stream()
        .map(this::buildOrderResponse)
        .toList();
  }

  @Override
  public OrderResponse getOrderResponseById(UUID id) {
    return jpaRepository.findById(id).map(this::buildOrderResponse).orElse(null);
  }

  @Override
  public OrderResponse create(OrderRequest order) {
    var client = orderHelper.fetchClient(order.getIdClient());
    if (client == null) {
      return null;
    }

    var books = order.getDetail().stream()
        .map(detail -> orderHelper.fetchBook(detail.getIdBook()))
        .filter(Objects::nonNull).toList();
    if (books.isEmpty()) {
      return null;
    }

    if (books.size() != order.getDetail().size()) {//cuando alguno de los libros no se ha encontrado pero al menos 1 sí
      return null;
    }

    var booksWithoutStock = order.getDetail().stream()//se valida que cada libro tenga stock suficiente
        .filter(detail -> {
          var book = books.stream()
              .filter(b -> b.id().equals(detail.getIdBook()))
              .findFirst().orElseThrow();
          return book.stock() < detail.getQuantity();
        }).map(OrderDetailRequest::getIdBook).toList();

    if (!booksWithoutStock.isEmpty()) {
      log.error("Los siguientes libros no tienen stock suficiente: {}", booksWithoutStock);
      return null;
    }

    var orderSaved = jpaRepository.save(orderHelper.setOrder(order, client, books));
    orderHelper.updateStock(order.getDetail(), books);

    return this.buildOrderResponse(orderSaved);
  }

  @Override
  public Order getById(UUID id) {
    return jpaRepository.findById(id).orElse(null);
  }

  @Override
  public OrderResponse save(Order order) {
    return this.buildOrderResponse(jpaRepository.save(order));
  }
}
