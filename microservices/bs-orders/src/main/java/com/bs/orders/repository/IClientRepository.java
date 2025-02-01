package com.bs.orders.repository;

import java.util.UUID;
import com.bs.orders.repository.dto.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clients", url = "${service.clients.url}")
public interface IClientRepository {

  @GetMapping("/bookstore/v1/clients/{id}")
  Client getClientById(@PathVariable("id") UUID id);
}
