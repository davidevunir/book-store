package com.bs.orders.repository.dto.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OrderResponse {

  UUID idBookOrder;
  UUID idClient;
  LocalDateTime createdAt;
  Double totalAmount;
  String status;
  List<OrderDetailResponse> orderDetailResponse;
}
