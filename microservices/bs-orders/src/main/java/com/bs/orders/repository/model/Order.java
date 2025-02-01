package com.bs.orders.repository.model;

import static lombok.AccessLevel.PRIVATE;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.AUTO;


import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BOOK_ORDER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class Order {

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "ID_BOOK_ORDER")
  UUID id;

  @NotNull(message = "El campo idClient es obligatorio")
  UUID idClient;

  LocalDateTime createdAt;

  @NotNull(message = "El campo totalAmount es obligatorio")
  Double totalAmount;

  @OneToMany(mappedBy = "order", cascade = ALL)
  List<OrderDetail> orderDetails;
}
