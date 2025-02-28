package com.bs.catalog.repository.model;

import static lombok.AccessLevel.PRIVATE;
import static jakarta.persistence.GenerationType.AUTO;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import java.util.UUID;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BOOK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class Book {

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "ID_BOOK")
  UUID id;

  @NotNull(message = "El campo title es obligatorio")
  String title;

  @NotNull(message = "El campo author es obligatorio")
  String author;

  String isbn;
  String synopsis;

  @Min(value = 1, message = "El rating no puede ser menor a 1")
  @Max(value = 5, message = "El rating no puede ser mayor a 5")
  Double rating;

  @Min(value = 0, message = "El precio no puede ser negativo")
  Double price;

  LocalDateTime publishedAt;
  Integer stock;
  Boolean active;
  String imageUrl;

  public void update(Book book) {
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.isbn = book.getIsbn();
    this.synopsis = book.getSynopsis();
    this.rating = book.getRating();
    this.price = book.getPrice();
    this.publishedAt = book.getPublishedAt();
    this.stock = book.getStock();
    this.active = book.getActive();
  }
}