package com.bs.orders.repository.jpa;

import java.util.UUID;
import com.bs.orders.repository.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {

}
