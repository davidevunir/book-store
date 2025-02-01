package com.bs.orders.repository.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public record Client(
    UUID id,
    String firstName,
    String lastName,
    String email,
    String address,
    String phone,
    Boolean active,
    LocalDateTime createdAt) {

}
