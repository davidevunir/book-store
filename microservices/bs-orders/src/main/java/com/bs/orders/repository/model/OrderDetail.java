package com.bs.orders.repository.model;

import static lombok.AccessLevel.PRIVATE;
import static jakarta.persistence.GenerationType.AUTO;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.UUID;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ORDER_DETAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "ID_ORDER_DETAIL")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "ID_BOOK_ORDER", nullable = false)
  Order order;

  @NotNull(message = "El campo idBook es obligatorio")
  UUID idBook;

  @NotNull(message = "El campo quantity es obligatorio")
  Integer quantity;

  @NotNull(message = "El campo price es obligatorio")
  Double price;

  @NotNull(message = "El campo subtotal es obligatorio")
  Double subtotal;
}
