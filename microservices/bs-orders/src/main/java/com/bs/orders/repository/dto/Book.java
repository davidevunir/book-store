package com.bs.orders.repository.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record Book(
    UUID id,
    String title,
    String author,
    String isbn,
    String synopsis,
    Double rating,
    Double price,
    LocalDateTime publishedAt,
    Integer stock,
    Boolean active) {

}
