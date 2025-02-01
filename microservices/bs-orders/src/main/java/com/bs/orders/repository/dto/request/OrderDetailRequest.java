package com.bs.orders.repository.dto.request;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OrderDetailRequest {

  UUID idBook;
  Integer quantity;
}
