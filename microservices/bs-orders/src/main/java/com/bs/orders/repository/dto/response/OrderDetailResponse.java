package com.bs.orders.repository.dto.response;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OrderDetailResponse {

  UUID idOrderDetail;
  UUID idBook;
  Integer quantity;
  Double price;
  Double subtotal;
}
