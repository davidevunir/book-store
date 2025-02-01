package com.bs.orders.repository.dto.request;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderRequest {

  @NotNull(message = "El campo idClient es obligatorio")
  UUID idClient;
  List<OrderDetailRequest> detail;
}
